package genomecomparison;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.RpcContext;

//BEGIN_HEADER
//END_HEADER

/**
 * <p>Original spec-file module name: GenomeComparison</p>
 * <pre>
 * </pre>
 */
public class GenomeComparisonServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;
    private static final String version = "0.0.1";
    private static final String gitUrl = "https://github.com/kbaseIncubator/proteome_comparison.git";
    private static final String gitCommitHash = "4e96f3a6193dfe5d7ddd0121f40192299b3b2f74";

    //BEGIN_CLASS_HEADER
    //END_CLASS_HEADER

    public GenomeComparisonServer() throws Exception {
        super("GenomeComparison");
        //BEGIN_CONSTRUCTOR
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: blast_proteomes</p>
     * <pre>
     * </pre>
     * @param   input   instance of type {@link genomecomparison.BlastProteomesParams BlastProteomesParams} (original type "blast_proteomes_params")
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "GenomeComparison.blast_proteomes", async=true)
    public String blastProteomes(BlastProteomesParams input, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN blast_proteomes
        returnVal = new BlastProteomes(authPart, config).run(input);
        //END blast_proteomes
        return returnVal;
    }
    @JsonServerMethod(rpc = "GenomeComparison.status")
    public Map<String, Object> status() {
        Map<String, Object> returnVal = null;
        //BEGIN_STATUS
        returnVal = new LinkedHashMap<String, Object>();
        returnVal.put("state", "OK");
        returnVal.put("message", "");
        returnVal.put("version", version);
        returnVal.put("git_url", gitUrl);
        returnVal.put("git_commit_hash", gitCommitHash);
        //END_STATUS
        return returnVal;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            new GenomeComparisonServer().startupServer(Integer.parseInt(args[0]));
        } else if (args.length == 3) {
            JsonServerSyslog.setStaticUseSyslog(false);
            JsonServerSyslog.setStaticMlogFile(args[1] + ".log");
            new GenomeComparisonServer().processRpcCall(new File(args[0]), new File(args[1]), args[2]);
        } else {
            System.out.println("Usage: <program> <server_port>");
            System.out.println("   or: <program> <context_json_file> <output_json_file> <token>");
            return;
        }
    }
}
