package environment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import environment.issue.Article;
import environment.issue.Guidance;
import shapes.*;

public class Handtools {
	public static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	public static final String genUID(List<?> list){
		int i = 0;
		
		while( i < list.size()){
			if(list.get(i) == null){
				break;
			}
			++i;
		}
		
		String indexToUID = Integer.toUnsignedString(i, 10);
		while(indexToUID.length() < 5){
			indexToUID = "0" + indexToUID;
		}
		
		return indexToUID;
	}
	
	public static final String summaryUserInfo(User user){
		
		String summary = "";
		String info[] = user.toString().split("#");
		ArrayList<String> info1 = new ArrayList<String>();
		
		for(int i=0; (i<3); ++i){
			if(i < info.length)
				info1.add(info[i]);
			else
				info1.add("¡Info not availiable!");
		}
		
		summary  =	"\tUnique ID: "		+	info1.get(0);
		summary +=	"\n\tUser Name: "	+	info1.get(1);
		summary +=	"\n\tE-Mail\t: "	+	info1.get(2);
		
		return summary;
	}
	
	public static final String summaryIssueInfo(Issue issue){
		
		String summary = "";
		String info[] = issue.toString().split("#");
		ArrayList<String> info1 = new ArrayList<String>();
		
		for(int i = 0; i < 6; ++i){
			if(i < info.length){
				info1.add(info[i]);
			}
			else
				info1.add("¡Info not available!");
		}
		
		summary  =	"\t\tUniqueID: "			+	info1.get(0);
		summary	+=	"\n\t\tTitle: "				+	info1.get(1);
		summary +=	"\n\t\tConference: "		+	info1.get(2);
		summary +=	"\n\t\tAuthors:\n"			+	info1.get(3);
		summary +=	"\n\t\tReleased Date: "		+	info1.get(4);
		summary +=	"\n\t\tRelated Project: "	+	info1.get(5);
		
		return summary;
	}
	
	public static final String summaryProjectInfo(Project project){
		
		String summary = "";
		String info[] = project.toString().split("#");
		ArrayList<String> info1 = new ArrayList<String>();
		
		for(int i = 0; i < 9; ++i){
			if(i < info.length){
				info1.add(info[i]);
			}
			else
				info1.add("¡Info not available!");
		}
		
		summary  =	"\t\tUniqueID: "		+	info1.get(0);
		summary +=	"\n\t\tTitle: "			+	info1.get(1);
		summary +=	"\n\t\tSponsor: "		+	info1.get(2);
		summary +=	"\n\t\tGoals: "			+	info1.get(3);
		summary +=	"\n\t\tDescription: "	+	info1.get(4);
		summary +=	"\n\t\tBudget R$: "		+	info1.get(5);
		summary +=	"\n\t\tStart date: "	+	info1.get(6);
		summary +=	"\n\t\tEnd date: "		+	info1.get(7);
		summary +=	"\n\t\tStatus: "		+	info1.get(8);
		
		return summary;
	}
	
	public static final boolean[] validatorUserInfo(String name, String email, String password[]){
		boolean validator[] = {!name.isEmpty() , email.contains("@"),
				(password[0].equals(password[1]) & !password[0].isEmpty() &
				(!((password[0].length() < 6) || (password[0].length() > 10))))}; // 0 - name, 1 - email, 2 - password
		return validator;
	}
	
	public static final Date helperValidatorStringDate(String patternDate){
		try {
			Date parsedDate = format.parse(patternDate);
			return parsedDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Sys.defaultTextErrorWithException(e, "That's all we know!");
			return null;
		}
	}
	
	public static final boolean helperValidatorBudget(String budget){
		boolean rtrn = false;
		try{
			Double.parseDouble(budget);
			rtrn  = true;
		}
		catch(NullPointerException e){
			Sys.defaultTextErrorWithException(e, "Null string is invalid!");
			rtrn = false;
		}
		catch(NumberFormatException e){
			Sys.defaultTextErrorWithException(e, "That's all we know!");
			rtrn = false;
		}
		
		return rtrn;
	}
	
	public static final boolean[] validatorProjectInfo(String title, String sponsor, String objective, String description, String budget, String startAt[], String endAt[]){
		boolean validator[] = {!title.isEmpty(), !sponsor.isEmpty(), !objective.isEmpty(), !description.isEmpty(), 
								helperValidatorBudget(budget), false, false}; // 0 - title, 1 - sponsor, 2 - objective, 3 - description
																							// 4 - budget, 5 - date start, 6 - date end
		
		
		Date start = helperValidatorStringDate(startAt[2]+"-"+startAt[1]+"-"+startAt[0]);
		Date end = helperValidatorStringDate(endAt[2]+"-"+endAt[1]+"-"+endAt[0]);
		
		validator[5] = (start != null);
		validator[6] = (end != null);
		
		if(validator[5] & validator[6]){
			validator[5] = validator[5] & (start.before(end));
			validator[6] = validator[6] & (end.after(start));
		}
		return validator;
	}
	
	public static final boolean[] validatorIssueInfo(String title, String conference, String releaseDate[]){
		boolean validator[] = {!title.isEmpty(), !conference.isEmpty(), false}; // 0 - title, 1 - conference, 2 - releseDate;
		
		Date released = helperValidatorStringDate(releaseDate[2]+"-"+releaseDate[1]+"-"+releaseDate[0]);
		validator[2] = (released != null);
		
		return validator;
	}
	
	public static final Object locateByUID(List<?> list, String uID) throws NumberFormatException {
		
		int uIDToIndex = -1;
		uIDToIndex = Integer.parseUnsignedInt(uID, 10);
		
		if(uIDToIndex < 0 || uIDToIndex == list.size()){
			return null;
		}
		
		else{
			return list.get(uIDToIndex);
		}
	}
	
	public static final ArrayList<User> locateUsersByAlias(List<User> list, String alias){
		ArrayList<User> matchs = new ArrayList<User>();
		User target = null;
		
		for(int i = 0; i < list.size(); ++i){
			target = list.get(i);
			if(target != null){
				if(target.getName().equals(alias)){
					matchs.add(target);
				}
			}
		}
		
		return matchs;
	}
	
	public static final ArrayList<Issue> locateIssuePerAuthor(List<Issue> list, User author){
		ArrayList<Issue> matchs = new ArrayList<Issue>();
		for(int i = 0; i < list.size(); ++i){
			if(list.get(i).lookforauthor(author)){
				matchs.add(list.get(i));
			}
		}
		
		Collections.sort(matchs);
		return matchs;
	}
	
	public static final int[] countProjectsByState(List<Project> list){
		int count[] = {0, 0, 0, list.size()}; // index: 0 - in draft, 1 - in progress, 2 - accomplished, 3 - total
		for(int i = 0; i < count[3]; ++i){
			
			if(list.get(i).getStatus().equals(Project.status0)){
				count[0]++;
			}
			else if(list.get(i).getStatus().equals(Project.status1)){
				count[1]++;
			}
			else if(list.get(i).equals(Project.status2)){
				count[2]++;
			}
		}
		
		return count;
	}
	
	public static final int[] countIssuePerType(List<Issue> list){
		int count[] = {0, 0, list.size()}; // 0 - Articles, 1 - Guidance, 2 - Total
		for(int i = 0; i < count[2]; ++i){
			
			if(list.get(i) instanceof Article){
				count[0]++;
			}
			else if(list.get(i) instanceof Guidance){
				count[1]++;
			}
		}
		
		return count;
	}
}
