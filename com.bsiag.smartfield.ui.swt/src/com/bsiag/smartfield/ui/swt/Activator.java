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
package com.bsiag.smartfield.ui.swt;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import com.bsiag.smartfield.client.ClientSession;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;

/** <h3>Activator</h3>
 *  All view ids and perspective ids are kept here.
*/
public class Activator implements BundleActivator{

  // the plugin id
  public static final String BUNDLE_ID = "com.bsiag.smartfield.ui.swt";
  // the initial perspective id
  public static final String PERSPECITVE_ID = "com.bsiag.smartfield.ui.swt.perspective.Perspective";
  // all view ids comodity to access.
  public static final String CENTER_VIEW_ID = "com.bsiag.smartfield.ui.swt.views.CenterView";
  public static final String TABLE_PAGE_VIEW_ID = "com.bsiag.smartfield.ui.swt.views.TablePageView";
  public static final String OUTLINE_VIEW_ID = "com.bsiag.smartfield.ui.swt.views.OutlinePageView";
  public static final String SEAECH_VIEW_ID = "com.bsiag.smartfield.ui.swt.views.SearchView";


  private ISwtEnvironment m_environment;
  // the shared instance
  private static Activator m_bundle;

  public void start(BundleContext context) throws Exception {
    m_bundle = this;
    m_environment = new SwtEnvironment(context.getBundle(), PERSPECITVE_ID, ClientSession.class);
  }

  public void stop(BundleContext context) throws Exception {
    m_bundle = null;
  }

  public static Activator getDefault() {
    return m_bundle;
  }

  public ISwtEnvironment getEnvironment() {
    return m_environment;
  }
}

