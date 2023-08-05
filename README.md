<!-- DO WE NEED A LOGO?  WHY NOT! -->
<div align="center">
  <img src="images/ofp.jpg" alt="Awful Logo">

  <h2 align="center">Office Floorplan Generator (Java Edition)</h3>
  <p align="center">
A floorplan generator for COMP-10019!
  </p>
</div>

## Stuff You Will Need to Know
* How to define the building envelopes
* How to control the generation details via the ``config.yml` file
* How to run the tool to generate the floorplan and company data

## Defining the Building Envelopes
Located within the resources folder, the envelopes folder contains a subfolder
for each defined building envelope.  Inside each subfolder are two templates,
named ``inner.txt`` and ``outer.txt`` that define the basic structure of the
building, including key spaces such as stairs/elevators, washrooms, etc.

These files must have identital shape definitions, but can have different
internal structures defined.  I have no idea if they should have parts in
common, or which elements are only used by the parser for ``inner.txt`` or
``outer.txt`` at the moment.

The structure of the ``inner.txt`` and ``outer.txt`` files is as follows:
* line 1: length of building
* line 2: width of building
* lines 3 to width+3: description of each 1x1 sqft tile

The description is a number that indicates the use of that tile. Valid usage
numbers are as follows:
* 0 - Undefined (open for use as office space or other room types)
* 1 - Unusable space (this is how irregular buildings shapes are defined)
* 2 - Stair and elevator space
* 3 - Corridors/walkways
* 4 - Washrooms
* 5 - Closets/utility rooms

Example ``inner.txt`` (for a very small building):
```
30
16
000003334444400000000000000000
000003334444400000000000000000
000003334444400000000000000000
000003334444400000000000000000
000003330000000000000000000000
000003330000000000003333111111
000003330000000000003333111111
000003333333333333333333111111
000003333333333333333333111111
000003333333333333333333111111
222223330000000000003333111111
222223330000000000003333111111
222223330000000000000000000000
222223330000000000000000000000
222223330000000000000000000000
```

## How to Generate Floorplans
Also inside the ``resources`` folder is the file ``config.yml``, which defines
key details of the floor definitions.  

The following parameters can be defined and configured:
* peoplePerOffice: - takes an integer value which defines how many people sit in a given 8x10 cubicle area (hint: use 1)
* overflowMin: - No idea 
* meetingRoomsPerFloor: - defines how many conference/meeting rooms will be added to a each floor (note that this is a maximum number, so it is possible that any number from 0 to the maximum defined here will be added) (hint: use 2)
* executiveOfficesClustered: - this boolean value changes the behaviour of the room allocation; when True it will cluster executive offices on one floor, but when False will place executives adjacent their respective departments
* are there more?
* I don't know

Example ``config.yml`` file:
```
# Allowed types of data: integer, string, boolean
peoplePerOffice: 1
overflowMin: 5

meetingRoomsPerFloor: 2 # recommended: 2

executiveOfficesClustered: False
```

## How to Generate Floorplans

