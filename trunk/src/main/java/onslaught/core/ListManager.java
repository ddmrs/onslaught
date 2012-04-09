package onslaught.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import onslaught.model.Entity;
import onslaught.model.turret.Turret;

/**
 *
 * @author EthiC
 */
public class ListManager {
   private HashMap<String,Collection<Object>> entityLists = new HashMap<String, Collection<Object>>();

   public ListManager() {
      entityLists.put("blat", new ArrayList<Object>());
   }

}
