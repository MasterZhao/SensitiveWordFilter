package test;

import java.io.IOException;

import org.junit.Test;

import utils.SolrConnectUtil;

public class SolrConnectUtilTest {

	@Test
	public void testGetSolrCore(){
		String solrcore=SolrConnectUtil.getSolrCore();
		System.out.println(solrcore);
	}
}
