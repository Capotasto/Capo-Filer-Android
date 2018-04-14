package com.funckyhacker.fileexplorer.util;

import org.junit.Assert;
import org.junit.Test;

public class FileUtilsTest {

  @Test public void getSizeStr() throws Exception {
    Assert.assertEquals("1023 B", FileUtils.getSizeStr(1023L));
    Assert.assertEquals("1.0 KB", FileUtils.getSizeStr(1024L));
    Assert.assertEquals("1.0 MB", FileUtils.getSizeStr(1024L * 1024L));
    Assert.assertEquals("1.0 GB", FileUtils.getSizeStr(1024L * 1024L * 1024L));
  }
}
