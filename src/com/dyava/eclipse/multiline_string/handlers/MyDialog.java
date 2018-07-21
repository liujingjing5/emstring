package com.dyava.eclipse.multiline_string.handlers;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MyDialog extends Dialog {
	String content;
	Text text;
    public MyDialog(Shell parentShell,String str) {
        super(parentShell);
        setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
        content=str;
    }
//    @Override
    protected Control createDialogArea(Composite parent) {
    	parent.getShell().setText("multiline string");
    	
    	parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite contents = new Composite(parent, SWT.NONE);
		contents.setLayoutData(new GridData(GridData.FILL_BOTH));
		contents.setLayout(new GridLayout(1,false));
		
		text = new Text(contents,SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL); 
    	text.setText(content);
    	text.setLayoutData(new GridData(GridData.FILL_BOTH));
    	return null;
    }
    @Override
    protected Point getInitialSize() {
    	return new Point(350, 283);
    }
    @Override
    protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {
        	content = text.getText();
        } else {
        	content = null;
        }
        super.buttonPressed(buttonId);
    }

    public String getText(){
    	return content;
    }
}
