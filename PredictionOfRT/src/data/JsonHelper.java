package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import mode.Recorder;
import mode.myDate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonHelper {

	ArrayList<String> IdSets_retweeted_status = new ArrayList<String>();
	String source;
	myDate date_threshold = new myDate(Recorder.date_threshold, "chinese");

	public static void main(String[] args) {

		new JsonHelper().loadUersAndTextProperty();
		// new JsonHelper().getRetweetedStatusID("874734542");
	}

	public void loadUersAndTextProperty() {

		String userString = null;
		String textString = null;

		// 创建写入流
		BufferedWriter writer = null;
		BufferedWriter writer_predict = null;
		BufferedWriter writerMaxRecorder = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File("twitterData\\Result_Train.txt"), true));
			writer_predict = new BufferedWriter(new FileWriter(new File("twitterData\\datasets_predict.txt"), true));
			writerMaxRecorder = new BufferedWriter(new FileWriter(new File("twitterData\\MaxRecorder.txt"), true));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<String> nodeName = new readFile().readNetByLine("twitterData\\network.txt");
		source = nodeName.get(0);

		// 加载中心节点转推的推文id
		this.getRetweetedStatusID(source);

		int i = 0;
		for (String nodeId : nodeName) {

			if (nodeId == source)
				continue;

			String JsonContext_User = new readFile().ReadFile("twitterData\\user_profile\\" + nodeId + ".json", 0);
			String JsonContext_Text = new readFile().ReadFile("twitterData\\tweets\\" + nodeId + ".json", 1);

			try {
				// 提取非中心用户属性信息
				JSONArray jsonArray_User = JSONArray.fromObject(JsonContext_User);
				JSONObject root_User = jsonArray_User.getJSONObject(0);
				JSONObject property_User = root_User.getJSONObject(nodeId);

				int isVerified;
				if (property_User.getBoolean(UserProperty.verified))
					isVerified = 1;
				else
					isVerified = 0;

				if (Recorder.max_followers_count_1 < Double.parseDouble(""+property_User.get(UserProperty.followers_count)))
					Recorder.max_followers_count_1 = Double.parseDouble(""+property_User.get(UserProperty.followers_count));
				if (Recorder.max_friends_count_2 < Double.parseDouble(""+property_User.get(UserProperty.friends_count)))
					Recorder.max_friends_count_2 = Double.parseDouble(""+property_User.get(UserProperty.friends_count));
				if (Recorder.max_statuses_count_3 < Double.parseDouble(""+property_User.get(UserProperty.statuses_count)))
					Recorder.max_statuses_count_3 = Double.parseDouble(""+property_User.get(UserProperty.statuses_count));
				if (Recorder.max_favourites_count_4 < Double.parseDouble(""+property_User.get(UserProperty.favourites_count)))
					Recorder.max_favourites_count_4 = (Double.parseDouble(""+property_User.get(UserProperty.favourites_count)));
				if (Recorder.max_listed_count_5 < Double.parseDouble(""+property_User.get(UserProperty.listed_count)))
					Recorder.max_listed_count_5 = Double.parseDouble(""+property_User.get(UserProperty.listed_count));
				if (Recorder.max_isVerified_6 < isVerified)
					Recorder.max_isVerified_6 = isVerified;

				userString = property_User.get(UserProperty.followers_count) + " "
						+ property_User.get(UserProperty.friends_count) + " "
						+ property_User.get(UserProperty.statuses_count) + " "
						+ property_User.get(UserProperty.favourites_count) + " "
						+ property_User.get(UserProperty.listed_count) + " " + isVerified;

				// 提取推文属性信息
				JSONArray jsonArray_Text = JSONArray.fromObject(JsonContext_Text);
				if (jsonArray_Text.size() > 0) {
					for (int j = 0; j < jsonArray_Text.size(); j++) {

						// 未设置隐私保护的
						JSONObject root_Text = jsonArray_Text.getJSONObject(j);
						JSONObject entities = root_Text.getJSONObject("entities");
						String text = root_Text.getString("text");
						JSONArray hashtags = entities.getJSONArray("hashtags");
						JSONArray user_mentions = entities.getJSONArray("user_mentions");
						JSONArray urls = entities.getJSONArray("urls");
						String statusId = root_Text.getString("id");
						myDate created_at = new myDate(root_Text.getString("created_at"));			
						
						
						int isRT;
						if (IdSets_retweeted_status.contains(statusId)) {
							isRT = 1;
							// 输出转推的推问ID
							System.out.println("转推的原始推文ID：" + statusId);
						} else
							isRT = -1;

						if (Recorder.max_hashtags_7 < (double) hashtags.size())
							Recorder.max_hashtags_7 = (double) hashtags.size();
						if (Recorder.max_user_mentions_8 < (double) user_mentions.size())
							Recorder.max_user_mentions_8 = (double) user_mentions.size();
						if (Recorder.max_urls_9 < (double) urls.size())
							Recorder.max_urls_9 = (double) urls.size();
						if (Recorder.max_text_length_10 < (double) getTextLength(text))
							Recorder.max_text_length_10 = (double) getTextLength(text);

						textString = hashtags.size() + " " + user_mentions.size() + " " + urls.size() + " "
								+ getTextLength(text) + " " + isRT;


						if (created_at.compare(date_threshold) == -1) {
							// 把抽取出的数据写入文件中
							if (writer != null) {
								writer.write(userString + " " + textString);
								writer.newLine();
							} 
						}
						else{
							if (writer_predict != null) {
								writer_predict.write(userString + " " + textString);
								writer_predict.newLine();
							}
						}

					}

				} else {
					// 设置隐私保护抓取不到推文的
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("warnning:" + nodeId);
				System.out.println(e);
			}

			System.out.println(i++);
		}
		System.out.println("end" + i + "end");

		try {
			if (writerMaxRecorder != null) {
				writerMaxRecorder.write("" + Recorder.max_followers_count_1);
				writerMaxRecorder.newLine();
				writerMaxRecorder.write("" + Recorder.max_friends_count_2);
				writerMaxRecorder.newLine();
				writerMaxRecorder.write("" + Recorder.max_statuses_count_3);
				writerMaxRecorder.newLine();
				writerMaxRecorder.write("" + Recorder.max_favourites_count_4);
				writerMaxRecorder.newLine();
				writerMaxRecorder.write("" + Recorder.max_listed_count_5);
				writerMaxRecorder.newLine();
				writerMaxRecorder.write("" + Recorder.max_isVerified_6);
				writerMaxRecorder.newLine();
				writerMaxRecorder.write("" + Recorder.max_hashtags_7);
				writerMaxRecorder.newLine();
				writerMaxRecorder.write("" + Recorder.max_user_mentions_8);
				writerMaxRecorder.newLine();
				writerMaxRecorder.write("" + Recorder.max_urls_9);
				writerMaxRecorder.newLine();
				writerMaxRecorder.write("" + Recorder.max_text_length_10);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 关闭写入流
		try {
			writer.close();
			writer_predict.close();
			writerMaxRecorder.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getTextLength(String text) {

		char character;
		boolean isNewWord = true;
		int length = 0;

		for (int i = 0; i < text.length(); i++) {
			character = text.charAt(i);
			if (character == ' ')
				isNewWord = true;
			else if (isNewWord == true) {
				length++;
				isNewWord = false;
			}

		}

		return length;
	}

	public void getRetweetedStatusID(String path) {

		String JsonContext_Text = new readFile().ReadFile("twitterData\\tweets\\" + path + ".json", 1);

		try {
			JSONArray jsonArray_Text = JSONArray.fromObject(JsonContext_Text);
			if (jsonArray_Text.size() > 0) {
				for (int j = 0; j < jsonArray_Text.size(); j++) {

					// 未设置隐私保护的
					JSONObject root_Text = jsonArray_Text.getJSONObject(j);
					JSONObject id_RT = root_Text.getJSONObject("retweeted_status");
					if (id_RT.size() != 0) {
						this.IdSets_retweeted_status.add(id_RT.getString("id"));
					}
				}

			} else {
				// 设置隐私保护抓取不到推文的
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
