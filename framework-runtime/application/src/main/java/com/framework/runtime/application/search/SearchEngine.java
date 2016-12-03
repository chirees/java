package com.framework.runtime.application.search;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.script.ScriptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;

public class SearchEngine  {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());
	
	private Client client;
	
	private boolean node;
	
	private String host;
	
	private int port;
	
	private String scripts;
	
	private static SearchEngine instance;
	
	private SearchEngine(boolean node, String host, int port, String scripts) {
		this.node = node;
		this.host = host;
		this.port = port;
		this.scripts = scripts;
	}
	
	public static synchronized SearchEngine  getInstance(boolean node, String host, int port, String scripts) {
		if(instance == null) {
			instance = new SearchEngine(node, host, port, scripts);
			try {
				instance.initClient();
			} catch (Exception e) {
				instance = null;
				logger.error("", e);
				return null;
			}
		}
		
		return instance;
	}
	
	public void initClient() throws Exception {
		if(node) {
			Node node = nodeBuilder().settings(ImmutableSettings.settingsBuilder().put("http.enabled", false))
				        .client(true).node();
			client = node.client();
		}
		else {
//			Settings settings = ImmutableSettings.settingsBuilder()
//			        .put("cluster.name", "benefit").build();
			
			client = new TransportClient()
	        .addTransportAddress(new InetSocketTransportAddress(host, port));
		}
		
		loadScripts(scripts);
	}
	
	
	private void loadScripts(String folderName) throws Exception {
		URL url = this.getClass().getClassLoader().getResource(folderName);
		
		if(url == null)
			return;
		
		String path = url.getFile();
		
		File file = new File(path);
		if(file.isDirectory()) {
			File[] childs = file.listFiles();
			for(File child:childs) {
				String name = child.getName();
				String scripts = FileUtils.readFileToString(child, "utf-8");
				
				logger.info("load search script[" + name + "]:" + scripts);
				
				String[] arr = name.split("\\.");
				client.preparePutIndexedScript(arr[1], arr[0], scripts).get();
			}
		}
	}

	
	public IndexResponse  create(String index, String document, String id, String json) {
		IndexResponse response = client.prepareIndex(index, document, id)
		        .setSource(json).execute()
		        .actionGet();
		
		return response;
	}
	
	public GetResponse  get(String index, String document, String id) {
		GetResponse response = client.prepareGet(index, document, id)
		        .execute()
		        .actionGet();
		
		return response;
	}
	
	public DeleteResponse  delete(String index, String document, String id) {
		DeleteResponse response = client.prepareDelete(index, document, id)
		        .execute()
		        .actionGet();
		
		return response;
	}
	
	public UpdateResponse  update(String index, String document, String id, String json) {
		UpdateResponse response = client.prepareUpdate(index, document, id).setDoc(json)
		        .get();
		
		return response;
	}
	
	public SearchResponse  search(String index, String document, String scriptName, Map params) {
		SearchResponse sr = client.prepareSearch(index).setTypes(document)
		        .setTemplateName(scriptName)
		        .setTemplateType(ScriptService.ScriptType.INDEXED)
		        .setTemplateParams(params)
		        .get();
		
		return sr;
	}

	
}
