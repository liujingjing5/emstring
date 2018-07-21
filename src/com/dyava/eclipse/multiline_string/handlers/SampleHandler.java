package com.dyava.eclipse.multiline_string.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.window.Window;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Shell shell = window.getShell();
		IWorkbenchPage page = window.getActivePage();
		IEditorPart part = page.getActiveEditor();
		if(part==null){
			MessageDialog.openWarning(window.getShell(),"warn","Please active java editor");
		}
		if(!ITextEditor.class.isAssignableFrom(part.getClass())){
			MessageDialog.openWarning(window.getShell(),"warn","Please active java editor");
		}
		IEditorInput input = part.getEditorInput();  
		ITextEditor textEditor = (ITextEditor)part;
		TextSelection textSelection =  (TextSelection)textEditor.getSelectionProvider().getSelection();
		String content = textSelection.getText();
		
		IDocument document = ((ITextEditor)part).getDocumentProvider().getDocument(input);  
//		SynchronizableDocument sd = (SynchronizableDocument)document;
		try {
			String fillStr= content.replaceAll(".+[\\r\\n]+(\\s+)\\S+[\\s\\S]+", "$1");
			if(fillStr.equals(content)){
				fillStr="";
			}
			String mcontent = JavaUtils.toMultiLine(content);
			MyDialog dialog = new MyDialog(shell,mcontent);
	        int res= dialog.open();
	        if(res==Window.OK){
		        String resStr = dialog.getText();
		        String scontent = JavaUtils.toSingleLine(resStr,fillStr);
		        document.replace(textSelection.getOffset(), textSelection.getLength(), scontent);
	        }
		} catch (Exception e) {
			MessageDialog.openError(
			window.getShell(),
			"error",
			e.getMessage());		
		}
		return null;
	}
}
