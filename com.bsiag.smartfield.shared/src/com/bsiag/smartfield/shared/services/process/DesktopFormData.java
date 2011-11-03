package com.bsiag.smartfield.shared.services.process;

import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;

public class DesktopFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public DesktopFormData() {
  }

  public City getCity() {
    return getFieldByClass(City.class);
  }

  public Language getLanguage() {
    return getFieldByClass(Language.class);
  }

  public class City extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public City() {
    }
  }

  public class Language extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Language() {
    }
  }
}
