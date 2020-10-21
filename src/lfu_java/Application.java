package lfu_java;

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


	public static void main(String[] args) {
    	
    	try { 
			File access = new File("C:\\Users\\Shalintha Silva\\Desktop\\LFU_squid_log\\lfu_caching_java\\resources\\access");
			FileReader fileReader = new FileReader(access);
			
			File mod_log = new File("C:\\Users\\Shalintha Silva\\Desktop\\LFU_squid_log\\lfu_caching_java\\resources\\mod_log.csv");
			FileWriter fileWriter = new FileWriter(mod_log);
			
			LFUCache cache = new LFUCache(5);
			
			@SuppressWarnings("unused")
			LocalDateTime time = LocalDateTime.now();
			
			bufferedReader = new BufferedReader(fileReader);

			String line;
			
			System.out.println("++++++++++++++++++++++++++++++++++Started+++++++++++++++++++++++++++++++++++");
			while((line = bufferedReader.readLine()) != null) {
				String[] line_content = line.split(" ");
				
				List<String> list = Arrays.asList(line_content);
				list = list.parallelStream().filter(s -> !s.isEmpty())
						.collect(Collectors.toList());
				
				@SuppressWarnings("unused")
				String duration = list.get(1);
				@SuppressWarnings("unused")
				String ip_addr = list.get(2);
				String result_code = list.get(3);
				String size = list.get(4);
				@SuppressWarnings("unused")
				String methode = list.get(5);
				String url = list.get(6);
				@SuppressWarnings("unused")
				String user = list.get(7);
				@SuppressWarnings("unused")
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