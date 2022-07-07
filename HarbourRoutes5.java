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

public class HarbourRoutes5 {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, Text>{

    private Text harbour = new Text();
    private Text route = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) {
	  String data[] = itr.nextToken().split(",");
        String harbourName=data[0];
        String routeName=data[2];
        harbour.set(harbourName);
        route.set(routeName);
        if(harbour.toString().equals("Midnightblue-Epsilon")){
        context.write(harbour,route);
        }
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
      for (Text val : values) {
        res = res+val;
      }
      result.set(res);
      context.write(key, result);
    }
  }
  public static class TokenizerMapper1
       extends Mapper<Object, Text, Text, Text>{

    private Text harbour = new Text();
    private Text route = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) {
        String data[] = itr.nextToken().split(",");
        String harbourName=data[0];
        String routeName=data[2];
        harbour.set(harbourName);
        route.set(routeName);
        context.write(route,harbour);
      }
    }
  }
  public static class IntSumReducer1
       extends Reducer<Text,Text,Text,Text> {
    private Text result = new Text();

    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      String res = new String();
      for (Text val : values) {
        res = res+" "+val;
      }
      result.set(res);
      context.write(key, result);
    }
  }
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "harbour routes");
    job.setJarByClass(HarbourRoutes5.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    job.waitForCompletion(true);

    Configuration conf1 = new Configuration();
    Job job1 = Job.getInstance(conf1, "word count1");
    job1.setJarByClass(WordCount.class);
    job1.setMapperClass(TokenizerMapper1.class);
    job1.setCombinerClass(IntSumReducer1.class);
    job1.setReducerClass(IntSumReducer.class);
    job1.setOutputKeyClass(Text.class);
    job1.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job1, new Path(args[0]));
    FileOutputFormat.setOutputPath(job1, new Path(args[2]));
    System.exit(job1.waitForCompletion(true) ? 0 : 1);
  }
}