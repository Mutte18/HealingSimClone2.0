# Spellcasting
I could potentially have a Spell casting handler class,
that adds the spell to be casted. 
This sets the cast time, cast time remaining of the spell

In game loop helper tick method, the spell's cast time can be incremented 
each 10th of a second

When the cast time is completed, it will trigger the actions/buffs and consume mana

I can have an endpoint that when called will cancel the spell currently
being casted, if there is one that is being cast.