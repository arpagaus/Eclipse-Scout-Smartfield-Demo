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
package com.bsiag.smartfield.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import com.bsiag.smartfield.shared.services.lookup.ILanguageLookupService;

public class LanguageLookupCall extends LookupCall{

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService> getConfiguredService() {
    return ILanguageLookupService.class;
  }
}
