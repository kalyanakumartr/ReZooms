package org.hbs.rezoom.bean.model;

import org.hbs.rezoom.util.EnumInterface;
import org.hbs.rezoom.util.ICRUDBean;

public interface IProducersAttachments extends ICommonFileUpload, IProducersBase, ICRUDBean
{
	public enum EDocument implements EnumInterface
	{
		Email_Verification_Failed, Fraudulent, Insufficient, Pending, Phone_Verification_Failed, Success
	}

	public String getDocumentDesc();

	public String getDocumentStatus();

	public void setDocumentDesc(String documentDesc);

	public void setDocumentStatus(String documentStatus);

}
