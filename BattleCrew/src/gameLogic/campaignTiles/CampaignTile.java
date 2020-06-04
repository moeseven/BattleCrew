package gameLogic.campaignTiles;

import java.io.Serializable;

import gameLogic.Campaign;

public abstract class CampaignTile implements Serializable{
	
	public abstract void enter(Campaign campaign);
	
}

