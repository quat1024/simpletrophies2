# Simple Trophies 2

In which I find out if modding is still fun or not.

For **Forge 1.20.1**. I don't have plans to write it for Fabric at this time.

## license

`LGPL-3.0-or-later`

## notes to self

* If you want to render both blockmodel + custom content, using a BEWLR apparently doesn't work unless you also set ENTITYBLOCK_ANIMATED, which removes the blockmodel. Not what i want. 	I'd like to avoid just re-rendering the blockmodel in the bewlr but maybe there's nothing wrong with that? I rerendered it in Simple Trophies 1 and it seemed to work ok.
* I'd like to change "name appears as a nametag-tooltip on hover" into "name appears on the GUI on hover". I think it'd be a lot tidier.
* The "earned at" date used the timezone of the server. I'd like to use the earner's local time (and also display it on hover)
* Simple Trophies 1 had a lot of NBT-hackery to implement trophy editing. Basically I didn't want to make a GUI (and I didn't know how to), so you could dye trophies by clicking with dye, rename them by using an anvil (I'd strip off the anvil nbt tags and move them to my custom name slot), etc. Bespoke stuff you had to read the README to figure out.
  * However I do like "click in the air to export the relevant NBT tag" - if i'm going to make a gui maybe not *click in the air*, but yeah I like a function to copy the relevant nbt tag with all the irrelevant parts removed (like the earned-at time)