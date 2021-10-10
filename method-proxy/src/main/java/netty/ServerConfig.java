package netty;

public class ServerConfig {

    private Integer port;

    private Integer bossThreadNum;

    private String bossThreadName;

    private Integer workerThreadNum;

    private String workerThreadName;


    public void check() {

    }


    public Integer getBossThreadNum() {
        return bossThreadNum;
    }

    public void setBossThreadNum(Integer bossThreadNum) {
        this.bossThreadNum = bossThreadNum;
    }

    public String getBossThreadName() {
        return bossThreadName;
    }

    public void setBossThreadName(String bossThreadName) {
        this.bossThreadName = bossThreadName;
    }

    public Integer getWorkerThreadNum() {
        return workerThreadNum;
    }

    public void setWorkerThreadNum(Integer workerThreadNum) {
        this.workerThreadNum = workerThreadNum;
    }

    public String getWorkerThreadName() {
        return workerThreadName;
    }

    public void setWorkerThreadName(String workerThreadName) {
        this.workerThreadName = workerThreadName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
