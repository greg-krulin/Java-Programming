package prog12;
//represents actually page we are looking at, creates an object on our computer for

/** This class represents the information stored in a file to record a
  * web page. */
public class PageFile {
  public final Long index;
  public final String url;
  private int refCount = 0;

  public PageFile (Long index, String url) {
    this.index = index;
    this.url = url;
  }

  public int getRefCount () { return refCount; }
  public void setRefCount(int ref) { refCount = ref; }
  public int incRefCount () { return ++refCount; }

  public String toString () {
    return index + "(" + url + ")" + refCount;
  }
}


    
