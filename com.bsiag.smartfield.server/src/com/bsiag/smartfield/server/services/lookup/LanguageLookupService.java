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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.osgi.framework.ServiceRegistration;

import com.bsiag.smartfield.shared.services.lookup.ILanguageLookupService;

public class LanguageLookupService extends AbstractLookupService implements ILanguageLookupService {

  Map<String, LookupRow> cache = new TreeMap<String, LookupRow>();

  @Override
  public void initializeService(ServiceRegistration registration) {
    super.initializeService(registration);

    for (Locale l : Locale.getAvailableLocales()) {
      if (StringUtility.isNullOrEmpty(l.getCountry())) {
        cache.put(l.getLanguage(), new LookupRow(l.getLanguage(), l.getDisplayName()));
      }
    }
  }

  public LookupRow[] getDataByAll(LookupCall call) throws ProcessingException {
    return cache.values().toArray(new LookupRow[cache.size()]);
  }

  public LookupRow[] getDataByKey(LookupCall call) throws ProcessingException {
    for (LookupRow r : cache.values()) {
      if (r.getKey().equals(call.getKey())) {
        return new LookupRow[]{r};
      }
    }
    return null;
  }

  public LookupRow[] getDataByRec(LookupCall call) throws ProcessingException {
    return null;
  }

  public LookupRow[] getDataByText(LookupCall call) throws ProcessingException {
    List<LookupRow> tmp = new ArrayList<LookupRow>();
    for (LookupRow r : cache.values()) {
      if (r.getText().toLowerCase().matches(StringUtility.toRegExPattern(call.getText()).toLowerCase()))
      {
        tmp.add(r);
      }
    }
    return tmp.toArray(new LookupRow[tmp.size()]);
  }
}
