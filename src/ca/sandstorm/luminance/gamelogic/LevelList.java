package ca.sandstorm.luminance.gamelogic;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;


public class LevelList
{
    private static final Logger _logger = LoggerFactory.getLogger(GameState.class);    
    
    private Vector<String> _levelList;
    private int _currentLevel;
    private boolean _packFinished;

    public LevelList(String levelList)
    {
	_logger.debug("LevelList(" + levelList + ")");
	
	_currentLevel = 0;
	_levelList = new Vector<String>();
	_packFinished = false;
	
	try {
	    // Open the file that is the first
	    // command line parameter
	    InputStream fstream = Engine.getInstance().getContext().getAssets().open(levelList);
	    
	    // Get the object of DataInputStream
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	    
	    // Read File Line By Line
	    while ((strLine = br.readLine()) != null) {
		_levelList.add(strLine);
	    }
	    
	    // Close the input stream
	    in.close();
	} catch (Exception e) {// Catch exception if any
	    _logger.error("Error: " + e.getMessage());
	}
    }
    
    public String getCurrentLevel()
    {
	return _levelList.get(_currentLevel);
    }
    
    public int getCurrentLevelIndex()
    {
	return _currentLevel;
    } 
    
    public void setCurrentLevel(int i)
    {
	_currentLevel = i;
	if (_currentLevel >= _levelList.size())
	{
	    _packFinished = true;
	    _currentLevel = _levelList.size() - 1;
	}	
    }
    
    public void iterateNextLevel()
    {
	_currentLevel++;
	if (_currentLevel >= _levelList.size())
	{
	    _packFinished = true;
	    _currentLevel = _levelList.size() - 1;
	}
    }
    
    public boolean isPackFinished()
    {
	return _packFinished;
    }
    
    public int getNumberOfLevels()
    {
	return _levelList.size();
    }
}
