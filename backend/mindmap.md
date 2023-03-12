# Spellcasting
I could potentially have a Spell casting handler class,
that adds the spell to be casted. 
This sets the cast time, cast time remaining of the spell

In game loop helper tick method, the spell's cast time can be incremented 
each 10th of a second

When the cast time is completed, it will trigger the actions/buffs and consume mana

I can have an endpoint that when called will cancel the spell currently
being casted, if there is one that is being cast.

# Game over scenarios
When the boss dies, the game should stop running, and write out a victory message
Later on I should face more bosses

If the boss manages to kill the whole raid, the game should stop running. And
a game over message should be printed

~~# Spell casting should not be possible if PLAYER is dead~~