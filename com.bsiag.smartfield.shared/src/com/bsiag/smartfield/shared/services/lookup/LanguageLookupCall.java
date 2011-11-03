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
