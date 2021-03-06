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
package com.bsiag.smartfield.server.services.custom.lucene.indexwriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.eclipse.scout.commons.BundleContextUtility;
import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.rt.shared.services.common.file.RemoteFile;
import org.eclipse.scout.service.AbstractService;

import com.bsiag.smartfield.shared.services.custom.lucene.indexwriter.IIndexWriterService;

public class IndexWriterService extends AbstractService implements IIndexWriterService {

  public void createZipIndex(RemoteFile file) {
    try {
      Set<String> zipCache = new HashSet<String>();

      String path = BundleContextUtility.resolve("${workspace_loc}/com.bsiag.smartfield.server/resources/index");
      IOUtility.deleteDirectory(path);
      Directory index = new SimpleFSDirectory(new File(path));
      IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_34, new StandardAnalyzer(Version.LUCENE_34));

      IndexWriter w;
      w = new IndexWriter(index, config);

      file.setCharsetName("UTF-8");
      BufferedReader reader = new BufferedReader(file.getDecompressedReader());
      String line;
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split("\t");
        if (tokens.length > 1) {
          String zip = tokens[0].trim();
          String city = tokens[1].trim();
          if (StringUtility.hasText(city) && StringUtility.hasText(zip)) {
            w.addDocument(createDocoment(zip, city));
            zipCache.add(zip + city);
          }
        }
      }

      w.close();

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Document createDocoment(String zip, String city) throws IOException {
    Document doc = new Document();
    doc.add(new Field("zip", zip, Field.Store.YES, Field.Index.ANALYZED));
    doc.add(new Field("city", city, Field.Store.YES, Field.Index.ANALYZED));
    return doc;
  }
}
