package com.healing.spell.spellcast.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpellCastRequest {
  private String spellId;
  private String targetId;
}
