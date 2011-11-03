/*******************************************************************************
 * Copyright (c) 2011 BSI AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Remo Arpagaus, BSI AG
 ******************************************************************************/
package com.bsiag.smartfield.server.services.lookup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.eclipse.scout.commons.BundleContextUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.osgi.framework.ServiceRegistration;

import com.bsiag.smartfield.shared.services.lookup.ICityLookupService;

public class CityLookupService extends AbstractLookupService implements ICityLookupService {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(CityLookupService.class);

  private IndexSearcher searcher;
  private MultiFieldQueryParser byTextQueryParser;
  private QueryParser byKeyQueryParser;

  @Override
  public void initializeService(ServiceRegistration registration) {
    super.initializeService(registration);

    String path = BundleContextUtility.resolve("${workspace_loc}/com.bsiag.smartfield.server/resources/index");
    try {
      Directory index = new SimpleFSDirectory(new File(path));
      searcher = new IndexSearcher(index);

      byKeyQueryParser = new QueryParser(Version.LUCENE_34, "zip", new StandardAnalyzer(Version.LUCENE_34));
      byTextQueryParser = new MultiFieldQueryParser(Version.LUCENE_34, new String[]{"zip", "city"}, new StandardAnalyzer(Version.LUCENE_34));
      byTextQueryParser.setDefaultOperator(QueryParser.Operator.AND);
    }
    catch (IOException e) {
      LOG.error(e.getMessage(), e);
    }
  }

  public LookupRow[] getDataByKey(LookupCall call) throws ProcessingException {
    try {
      Query query = byKeyQueryParser.parse((String) call.getKey());
      return queryIndex(query, 1);
    }
    catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new ProcessingException("Failed to query index");
    }
  }

  public LookupRow[] getDataByText(LookupCall call) throws ProcessingException {
    try {
      Query query = byTextQueryParser.parse(call.getText().replaceAll(" \\*", ""));
      return queryIndex(query, call.getMaxRowCount());
    }
    catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new ProcessingException("Failed to query index");
    }
  }

  private LookupRow[] queryIndex(Query query, int maxRowCount) throws IOException, CorruptIndexException {
    TopScoreDocCollector collector = TopScoreDocCollector.create(maxRowCount, true);
    searcher.search(query, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;

    List<LookupRow> rows = new ArrayList<LookupRow>();
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      String key = d.get("zip");
      String text = key + " " + d.get("city");
      rows.add(new LookupRow(key, text));
    }
    return rows.toArray(new LookupRow[rows.size()]);
  }

  public LookupRow[] getDataByAll(LookupCall call) throws ProcessingException {
    return new LookupRow[0];
  }

  public LookupRow[] getDataByRec(LookupCall call) throws ProcessingException {
    return new LookupRow[0];
  }
}
