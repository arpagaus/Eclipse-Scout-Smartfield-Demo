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
package com.bsiag.smartfield.client.ui.forms;

import java.util.Locale;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.shared.ScoutTexts;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import com.bsiag.smartfield.client.ui.forms.DesktopForm.MainBox.CityField;
import com.bsiag.smartfield.client.ui.forms.DesktopForm.MainBox.LanguageField;
import com.bsiag.smartfield.shared.Texts;
import com.bsiag.smartfield.shared.services.lookup.CityLookupCall;
import com.bsiag.smartfield.shared.services.lookup.LanguageLookupCall;
import com.bsiag.smartfield.shared.services.process.DesktopFormData;

@FormData(value = DesktopFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class DesktopForm extends AbstractForm {

  public DesktopForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredDisplayViewId() {
    return VIEW_ID_CENTER;
  }

  public CityField getCityField() {
    return getFieldByClass(CityField.class);
  }

  public LanguageField getLanguageField() {
    return getFieldByClass(LanguageField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class CityField extends AbstractSmartField<String> {

      @Override
      protected String getConfiguredLabel() {
        return Texts.get("City");
      }

      @Override
      protected Class<? extends LookupCall> getConfiguredLookupCall() {
        return CityLookupCall.class;
      }
    }

    @Order(20.0)
    public class LanguageField extends AbstractSmartField<String> {

      @Override
      protected String getConfiguredLabel() {
        return ScoutTexts.get("Language");
      }

      @Override
      protected Class<? extends LookupCall> getConfiguredLookupCall() {
        return LanguageLookupCall.class;
      }
    }
  }

  public class ViewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      getCityField().setValue("71638");
      getLanguageField().setValue(Locale.getDefault().getLanguage());
    }
  }

  public void startView() throws ProcessingException {
    startInternal(new DesktopForm.ViewHandler());
  }
}
