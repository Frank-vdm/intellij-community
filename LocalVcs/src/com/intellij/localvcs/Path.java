package com.intellij.localvcs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Path {
  // todo support c:/ notation
  private static final String DELIM = "/";
  private String myPath;

  public Path(String path) {
    myPath = path;
  }

  public Path(DataInputStream s) throws IOException {
    myPath = s.readUTF();
  }

  public void write(DataOutputStream s) throws IOException {
    s.writeUTF(myPath);
  }

  public String getName() {
    return getParts().get(getParts().size() - 1);
  }

  public boolean isRoot() {
    return getParent() == null;
  }

  public Path getParent() {
    List<String> parts = getParts();
    parts.remove(parts.size() - 1);

    if (parts.isEmpty()) return null;

    String result = "";
    for (String p : parts) {
      result += p + DELIM;
    }

    return new Path(result.substring(0, result.length() - 1));
  }

  public Path appendedWith(String tail) {
    return new Path(myPath + DELIM + tail);
  }

  public Path renamedWith(String newName) {
    if (isRoot()) return new Path(newName);
    return getParent().appendedWith(newName);
  }

  protected List<String> getParts() {
    List<String> result = new ArrayList<String>();

    StringTokenizer t = new StringTokenizer(myPath, DELIM);
    while (t.hasMoreTokens()) {
      result.add(t.nextToken());
    }

    return result;
  }

  @Override
  public boolean equals(Object o) {
    // todo possible we should remove these methods  
    return myPath.equals(((Path)o).myPath);
  }

  @Override
  public int hashCode() {
    throw new UnsupportedOperationException();
  }
}
