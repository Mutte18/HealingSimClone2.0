package com.healing.spell.spellqueue;

import java.util.ArrayList;
import java.util.Optional;

public class SpellQueue extends ArrayList<SpellQueueItem> {
  public void addSpellToQueue(SpellQueueItem spellQueueItem) {
    this.add(spellQueueItem);
  }

  public Optional<SpellQueueItem> getSpellQueueItemById(String id) {
    return this.stream().filter(spellQueueItem -> spellQueueItem.getId().equals(id)).findAny();
  }

  private void removeSpellFromQueue(SpellQueueItem spellQueueItem) {
    this.remove(spellQueueItem);
  }

  public Optional<SpellQueueItem> processFirstSpellQueueItem() {
    var spellQueueItem = this.stream().findFirst();
    spellQueueItem.ifPresent(this::removeSpellFromQueue);
    return spellQueueItem;
  }
}
