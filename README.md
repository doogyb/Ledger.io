
# revolut-listener	

revolut-listener is intended to be a simple app which listens to the notifications the [Revolut](www.revolut.com) app makes when using it to purchase products. The aim of this app is to replace the original functionality offered by Revolut which tracked your spending and notified how much you had spend in a given day, and how much you had left to spend in a given budget period.

## Built With

* [Android Jetpack]([https://developer.android.com/jetpack](https://developer.android.com/jetpack)) - Android Library Suite.

### Todo

- [ ] Implement Settings
	- [ ] Currency setting
	- [ ] Daily limits, Monthly Budgets
- [ ] Fix TextView layout centering issue
- [ ] ViewModel update on Notification Ping, update on `onResume()` 
- [ ] Visual Representation of Budget remaining (simple Progressbar for now)
- [ ] Budget and Spending breakdown
	- [ ] Graph showing spending / time
	- [ ] Types of spending (if possible?)
	

### In Progress

- [ ] Parse Notification for spend amount
- [ ] Remove redundant Spend class - this can be computed using historical budget info

### Done ✓

- [x] Create TODO.md
- [x] Implement ViewModel for RemainingMoneyFragment
- [x] Simple layout for RemainingMoneyFragment
- [x] Create Schema for the DB, create DB and DAOs, implement update methods, test they work.
- [x] Build Service that listens to Notifications, update DB when Notification pings