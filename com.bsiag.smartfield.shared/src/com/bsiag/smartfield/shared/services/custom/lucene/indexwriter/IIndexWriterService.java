package com.bsiag.smartfield.shared.services.custom.lucene.indexwriter;

import org.eclipse.scout.rt.shared.services.common.file.RemoteFile;
import org.eclipse.scout.service.IService;

public interface IIndexWriterService extends IService {

  void createZipIndex(RemoteFile file);

}
