package nettyTcp;

public class StringInfo {
    private int len;
    private String content;

    public StringInfo(int len, String content) {
        this.len = len;
        this.content = content;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
