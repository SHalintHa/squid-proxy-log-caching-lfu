import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Application {
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
    	
    	try { 
			File access = new File("access");
			FileReader fileReader = new FileReader(access);
			
			File mod_log = new File("mod_log.csv");
			FileWriter fileWriter = new FileWriter(mod_log);
			
			LFUCache cache = new LFUCache(5);
			
			LocalDateTime time = LocalDateTime.now();
			
			bufferedReader = new BufferedReader(fileReader);

			String line;
			
			System.out.println("++++++++++++++++++++++++++++++++++Started+++++++++++++++++++++++++++++++++++");
			while((line = bufferedReader.readLine()) != null) {
				String[] line_content = line.split(" ");
				
				List<String> list = Arrays.asList(line_content);
				list = list.parallelStream().filter(s -> !s.isEmpty())
						.collect(Collectors.toList());
				
				String duration = list.get(1);
				String ip_addr = list.get(2);
				String result_code = list.get(3);
				String size = list.get(4);
				String methode = list.get(5);
				String url = list.get(6);
				String user = list.get(7);
				String ftype = list.get(8);
				
				String str_to_write = result_code + " " + size;
				cache.set(url, str_to_write);
			}
			
			bufferedReader.close();
			
			bufferedWriter = new BufferedWriter(fileWriter);
			cache.write_cache(bufferedWriter);
			bufferedWriter.close();
			Thread.sleep(3000);
			
			System.out.println("++++++++++++++++++++++++++++++Finished Caching++++++++++++++++++++++++++++++");
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
    }
}