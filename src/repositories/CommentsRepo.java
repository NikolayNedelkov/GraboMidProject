package repositories;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import dataclasses.Comment;
import dataclasses.DestinationTrader;
import exceptions.CommentException;

public class CommentsRepo {
	
	private static File f;
	private static CommentsRepo commentsRepo = null;
	private List<Comment> allComments;
	
	private CommentsRepo() {
		this.allComments = new ArrayList<Comment>();
		this.allComments = getDestinationTradersCommentsToJSONFile();
	}
	
	public static CommentsRepo getInstance() {
		if(commentsRepo==null) {
			commentsRepo = new CommentsRepo();
		}
		return commentsRepo;
	}
	
	public static List<Comment> getDestinationTradersCommentsToJSONFile() {
		Gson gson = new Gson();
		List<Comment> list = null;
		try (Reader reader = new FileReader(f)) {
			JsonElement json = gson.fromJson(reader, JsonElement.class);
			String jsonInString = gson.toJson(json);

			list = gson.fromJson(jsonInString, new TypeToken<List<Comment>>() {
			}.getType());

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (list == null) {
			list = new ArrayList<>();
		}
		return list;

	}

	private void writeDestinationTradersCommentsToJSONFile(List<Comment> comments) {
		Gson gson = new Gson();
		try (FileWriter writer = new FileWriter(f)) {
			gson.toJson(comments, writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addNewComment(Comment c, DestinationTrader d) throws CommentException, IOException {
		if (c == null)
			throw new CommentException("Invalid comment");
		CommentsRepo.f = new File(
				"Json" + File.separator + ("comments" + c.getDestination().getDestinationTraderID()) + ".json");
		if (!CommentsRepo.f.exists()) {
			CommentsRepo.f.createNewFile();
		}
		allComments.add(c);
		d = c.getDestination();
		d.addComment(c);
		this.writeDestinationTradersCommentsToJSONFile(allComments);

	}
}
