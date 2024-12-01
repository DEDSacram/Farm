# Farm Simulator Project To-Do List

## 1. Game Setup
- [X] Initialize game with starting conditions:
  - 1 field for planting crops.
  - Starting capital (e.g., 100 coins).
  - A storage system to hold crops and products.
  - Access to the market for buying and selling.

## 2. Market System
- [X] Implement market mechanics:
  - [X] Buy crops (prices change randomly each round, e.g., wheat 1-3 coins). # Would have to implement Stale logic to avoid reselling prices are random only when shop is rotated
  - [X] Sell crops and animal products (prices change dynamically, e.g., eggs 3-5 coins).  # Would have to implement Stale logic to avoid reselling prices are random only when shop is rotated

## 3. Crop System
- [X] Implement crop mechanics:
  - [X] Enable planting crops on available fields.
  - [X] Set different growth durations for each crop (e.g., wheat 6 rounds).
  - [X] Implement yield percentages after harvest (e.g., 300-500% of planted crops).
  - [X] Implement random disasters (e.g., drought, mold, frost) affecting crops. # Just one type affects either crops or animals at low percentage older animals have a chance to die to it

## 4. Animal System
- [X] Implement animal mechanics:
  - [X] Buy animals (e.g., cows, chickens).
  - [X] Each animal produces a product every round (e.g., milk, eggs).
  - [X] Each animal requires a specific crop for food and a certain amount for survival.
  - [X] If animals are not fed, they die.

## 5. Farm Expansion
- [X] Allow player to invest in farm expansion:
  - [X] Buy additional fields (e.g., 400 coins per field).
  - [X] Buy more animals (e.g., chickens cost 50 coins each).

## 6. Round Mechanics
- [X] Implement round actions and evaluations:
  - [X] Feed animals (consume crops from storage).
  - [X] Animals produce products (added to storage).
  - [X] Crop growth (harvest crops once they reach maturity).
  - [X] Random disasters may affect crop yields.


## 7. Optional Features
- [ ] Optional game expansion features:
  - [ ] Add different types of animals and crops.
  - [ ] Implement animal housing (e.g., chicken coop, barn) with a capacity limit.
  - [ ] Implement save/load game feature from a file.


## 8. Code Structure and Organization
- [X] Follow OOP principles and encapsulation. # I think I did
- [X] Organize classes into at least two packages.
- [X] Implement at least one enum type.
- [X] Handle all user inputs appropriately.

## 9. Crop and Animal Design
- [X] Implement at least 3 types of crops and 2 types of animals.
- [ ] Design appropriate values (e.g., prices, growth times) for balance and fun gameplay. # Not a gameplay designer, game is not balanced might fix until exam
