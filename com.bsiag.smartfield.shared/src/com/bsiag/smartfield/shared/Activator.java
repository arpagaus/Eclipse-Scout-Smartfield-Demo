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
package com.bsiag.smartfield.shared;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{

  public static String PLUGIN_ID="com.bsiag.smartfield.shared";

  private static Activator plugin;

  public static Activator getDefault(){
    return plugin;
  }

  public void start(BundleContext context) throws Exception{
    plugin=this;
  }

  public void stop(BundleContext context) throws Exception{
    plugin=null;
  }
}

