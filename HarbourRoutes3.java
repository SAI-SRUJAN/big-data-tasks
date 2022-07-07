import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HarbourRoutes3 {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, Text>{

    private Text harbour = new Text();
    private Text route = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) {
        String data[] = itr.nextToken().split(",");
        String harbourName = data[0];
        String routeName = data[2];
        harbour.set(harbourName);
        route.set(routeName);
        context.write(route, harbour);
      }
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,Text,Text,Text> {
        private Text result = new Text();
  public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      String res = new String();
      for (Text val:values) {
        res = res + " " + val;
      }
      result.set(res);
      if(key.toString().equals("Carnation_Sixty-seven")){
          context.write(key, result);
        }
    }
  }
public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "harbour routes");
    job.setJarByClass(HarbourRoutes3.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
