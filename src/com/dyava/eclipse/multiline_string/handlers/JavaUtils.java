package com.dyava.eclipse.multiline_string.handlers;


public class JavaUtils {  
	
	public static String toSingleLine(String str,String fillStr) {
		if(str==null){
			return "\"\"";
		}else{
			str = str.trim();
			if("".equals(str)){
				return "\"\"";
			}
		}
		
		StringBuilder sb = new StringBuilder("\"");
		int len = str.length();
		for(int i=0; i<len; i++){
			char b = str.charAt(i);
			if(b=='t'){
				sb.append("\t");
			}else if(b=='\n' || b=='\r'){
				if(b=='\n'){
					sb.append("\\n");
					sb.append("\"+\n").append(fillStr).append("\"");
				}else if(b=='\r'){
					sb.append("\\r");
					if(i+1<len){
						char bnext = str.charAt(i+1);
						if(bnext=='\n'){
							sb.append("\\n");
							sb.append("\"+\n").append(fillStr).append("\"");
							i++;
						}else{
							sb.append("\"+\r").append(fillStr).append("\"");
						}
					}else{
						sb.append("\"+\r").append(fillStr).append("\"");
					}
				}	
			}else if(b=='\\'){
				sb.append("\\\\");
			}else if(b=='"'){
				sb.append("\\\"");
			}else{
				sb.append(b);
			}

		}
		sb.append("\"");
		return sb.toString();
	}

	public static String toMultiLine(String str) {
		if(str==null){
			return "";
		}else{
			str = str.trim();
			if("".equals(str)){
				return "";
			}
		}
		if(str.charAt(0)!='"' || str.charAt(str.length()-1)!='"'){
			throw new RuntimeException("Must select string,start and end with\"");
		}
		
		int state = 0; 
		StringBuilder sb = new StringBuilder();
		StringBuilder fragment = new StringBuilder();
		int len = str.length();
		for(int i=0; i<len; i++){
			char b = str.charAt(i);
			if(state==0){
				if(b=='"'){
					state=1;
				}
			}else if(state==1){
				if(b=='"'){
					state=0;
					if(fragment.length()>0){
						sb.append(fragment);
						fragment.delete(0,fragment.length());
					}
				}else if( b=='\\'){
					if(i+1<len){
						char bnext = str.charAt(i+1);
						if(bnext=='t'){
							fragment.append('\t');
						}else if(bnext=='n'){
							fragment.append('\n');
						}else if(bnext=='r'){
							fragment.append('\r');
						}else if(bnext=='\\'){
							fragment.append('\\');
						}else if(bnext=='\"'){
							fragment.append('\"');
						}else{
							throw new RuntimeException("Unhandled escape characters");
						}
						i++;
					}else{
						break;
					}
				}else{
					fragment.append(b);
				}
			}
		}
		if(fragment.length()>0){
			sb.append(fragment);
		}
		return sb.toString();
	}
 
}  

