---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Client2Door User Guide


Client2Door is a **lightweight desktop address management app, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). It will  that helps users keep track of subscriber deliveries and related operational details. If you can type fast, Client2Door can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your Client2Door app.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar Client2Door.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Release1.4-New_UI.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 Singapore 012345 b/box-1 ex/2026-12-31` : Adds a person with a box, default remark, expiry date, and "Pending" delivery status.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

![help message](images/helpMessage.png)

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [o/REMARK] ex/EXPIRY_DATE b/BOX… [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags and boxes (including 0 tags, but at least 1 `b/BOX`). If `o/REMARK` is omitted, the person is created with the default remark `No remark`.
</box>

**Important Note:** The expiry date input will be applied to **ALL** Boxes added in that same command.
**Important Note:** Default delivery status is set to "Pending"

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 Singapore 012345 b/box-1 ex/2026-12-31`
* `add n/Betsy Crowe p/1234567 e/betsycrowe@example.com a/Newgate Prison 123456 b/box-1 b/box-2 o/weekly pastry set ex/2026-12-15 t/friend t/criminal`

### Importing subscribers : `import`

Imports subscribers from a CSV file into the address book.

Format: `import FILE_PATH`

- Reads subscriber data from the specified CSV file.
- The file must be a valid .csv file.
- The first row is treated as a header and will be ignored.
- Each subsequent row must contain the required fields.
- Each valid row is converted into a subscriber and added to the address book.
- Import uses the same validation rules as the add command.
- Invalid or duplicate entries are skipped, but the import will continue.
- A summary is shown after execution, including:
  - Number of successfully imported subscribers
  - Total rows processed
  - Details of failed rows (if any)
  
<box type="tip" seamless>

Tip: You can collect your data through a google form, then export it as a .csv file.

</box> 

<box type="tip" seamless>

Tip: Place your CSV files in the `data/` folder of the project for easier access and consistent file paths.
</box>

<box type="warning" seamless>

Caution:

Rows with missing fields will fail to be imported.
Ensure fields follow the correct formats (e.g. valid email, phone number, expiry date), or the row will fail to import.
Even if some rows fail, the rest will still be imported.
</box>

Examples:
- `import data/subscribers.csv` imports subscribers from the specified file.

### Adding one or more boxes to a person: `addbox`

Adds one or more boxes to a person in the address book.

Format: `addbox n/NAME b/BOX_NAME [b/BOX_NAME]... ex/EXPIRY_DATE`

* Adds box(es) with the box names listed to the person identified by `NAME`.
* The expiry date input will be applied to all added boxes on that same command.
* Accepts one or more box names.

Examples:
* `addbox n/Amy b/box-1 ex/2026-12-31` adds 1 box-1 with an expiry date of 2026-12-31 to Amy.
* `addbox n/Amy b/box-1 b/box-2 ex/2026-12-31` adds 2 boxes box-1 and box2, both with an expiry date of 2026-12-31 to Amy.

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [o/REMARK] [ex/EXPIRY_DATE] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e. adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.
* You can update the remark either with `edit ... o/NEW_REMARK` or with the dedicated [`remark`](#updating-a-persons-remark--remark) command below.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person.
*  `edit 2 n/Betsy Crower o/prefers morning delivery t/` Edits the name and remark of the 2nd person and clears all existing tags.

### Editing a box of a person : `editbox`

Edits an existing box of an existing person in the address book.

Format: `edit n/NAME b/BOX_NAME [nb/NEW_BOX_NAME] [ex/EXPIRY_DATE]`

* Edits the person specified by the person's `NAME`.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
* `editbox n/Amy b/box-1 nb/box-2` Edits the name of box-1 under Amy to box-2.
* `editbox n/Amy b/box-1 ex/2026-12-31` Edits the expiry date of box-1 under Amy.
* `editbox n/Amy b/box-1 nb/box-2 ex/2026-12-31` Edits the name and expiry date of box-1 under Amy.

### Updating a person's remark : `remark`

Updates the remark of an existing person in the address book.

Format: `remark INDEX REMARK`

* Updates the remark of the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `remark 1 prefers morning delivery`
* `remark 2 allergic to peanuts`

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `John Doe` and `John Ching`
  ![result for 'find alex david'](images/findJohnResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX` OR `delete EMAIL`

* Deletes the person at the specified `INDEX` or `EMAIL`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* The email must be an existing email in the subscribers list.

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Delete boxes from a person: `deletebox`

Deletes one or more boxes for a specified person from the address book

Format: `deletebox n/NAME b/BOX_NAME [b/BOX_NAME]...`

* Deletes one or more boxes specified by their `BOX_NAME` for a person specified by their `NAME`.
* At least one box must be provided.
* If all boxes are deleted from the specified person, the person will automatically be deleted from the address book.

Examples:
* `deletebox n/Amy b/box-1` deletes the box named box-1 from Amy.
* `deletebox n/Amy b/box-1 b/box-2` deletes the boxes named box-1 and box-2 from Amy. If Amy only has boxes box-1 and
box-2, this command will delete Amy from the address book upon execution.

=======
### Marking a person's delivery status : `mark`

Marks the specified person's delivery status to a specified status.

Format: `mark INDEX STATUS`

* Marks the person at the specified `INDEX` with the specified `STATUS`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* The status **must be** `PENDING`, `PACKED` or `DELIVERED` (not case-sensitive) 

Examples:
* `mark 1 delivered`

### Filter subscribers: `filter`

Displays subscribers in the address book based on the box type they have or the driver they are assigned to.

Format: `filter [BOX_NAME]` OR `filter [d/DRIVER_NAME]`

* Filters the displayed list of subscribers by `BOX_TYPE`, `DRIVER_NAME`.
* At least one of the optional fields must be provided.

Examples:

* `filter d/Alex` displays all subscribers assigned to driver Alex.
* `filter Vegetable` displays all subscribers who have a Vegetable box.

### Assigning drivers to subscribers : `assign`

Assigns one or more drivers to groups of subscribers so that each subscriber is tagged with an assigned driver.

Format: `assign n/DRIVER_NAME p/DRIVER_PHONE [n/DRIVER_NAME p/DRIVER_PHONE]...`

* Assigns the given drivers to **all subscribers currently in the list**.
* The number of `n/ ... p/ ...` pairs provided determines how many subscriber groups (clusters) will be formed.
* Drivers provided must be **unique** (duplicate drivers are not allowed).
* Any existing driver tag on a subscriber will be replaced with the newly assigned driver tag.

Examples:
* `assign n/John Doe p/91234567` Assigns all subscribers to John Doe.
* `assign n/John Doe p/91234567 n/Jane Tan p/98765432` Splits subscribers into 2 groups and assigns each group to John Doe and Jane Tan respectively.
* `assign n/John Doe p/91234567 n/Jane Tan p/98765432 n/Ali Bin p/81234567` Splits subscribers into 3 groups and assigns each group to a driver.

### Exporting driver delivery assignments: `export`

Format: `export [FILE_PATH]`

Generates an HTML file showing all drivers and their assigned subscribers as shown below.
![ExportedHTML.png](images/exportedHTML.png)

- If FILE_PATH is not provided, the file will be saved to the default location: data/delivery_assignments.html.
- If a file already exists at the specified path, it will be overwritten.
- The `FILE_PATH` must end with `.html`
- This command only works if there are existing delivery assignments.
<box type="tip" seamless>

Tip: Open the exported .html file in any web browser to view a clean, styled summary of all delivery assignments.
</box>

<box type="warning" seamless>

Caution:
If there are no delivery assignments, the export will fail and display an error message.
Ensure that drivers have been assigned persons before running this command.
</box>

Examples:

`export`
Exports delivery assignments to data/delivery_assignments.html.

`export fp/data/my_assignments.html`
Exports delivery assignments to the specified file path.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action         | Format, Examples                                                                                                                                                                                               |
|----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**        | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/BOX [o/REMARK] ex/EXPIRY_DATE [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 123465 b/box-1 ex/2026-12-31 t/friend` |
| **Clear**      | `clear`                                                                                                                                                                                                        |
| **Delete**     | `delete INDEX` or `delete EMAIL`<br> e.g., `delete 3` `delete test@example.com`                                                                                                                                |
| **Edit**       | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [b/BOX] [o/REMARK] [ex/EXPIRY_DATE] [t/TAG]…​`<br> e.g., `edit 2 n/James Lee o/prefers morning delivery`                                           |
| **Find**       | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                     |
| **Filter**     | `filter [BOX_NAME] [d/DRIVER_NAME]`<br> e.g., `filter d/Alex` or `filter box-1`                                                                                                                                |
| **Mark**       | `mark INDEX STATUS`<br> e.g., `mark 1 delivered`                                                                                                                                                               |
| **Remark**     | `remark INDEX REMARK`<br> e.g., `remark 2 allergic to peanuts`                                                                                                                                                 |
| **Assign**     | `assign n/NAME p/PHONE_NUMBER [n/NAME] [p/PHONE_NUMBER]…`<br> e.g., `assign n/John Doe p/91234567 n/Jane Tan p/98765432`                                                                                       |
| **List**       | `list`                                                                                                                                                                                                         |
| **Add Box**    | `addbox n/NAME b/BOX [b/BOX_NAME]… ex/EXPIRY_DATE` <br> e.g., `addbox n/Amy b/box-1 b/box-2 ex/2026-12-31…​`                                                                                                   |
| **Edit Box**   | `editbox n/NAME b/BOX_NAME [nb/NEW_BOX_NAME] [ex/EXPIRY_DATE]` <br> e.g., `editbox n/Amy b/box-1 nb/box-2 ex/2026-12-31`                                                                                       |
| **Delete Box** | `deletebox n/NAME b/BOX_NAME [b/BOX_NAME]…` <br> e.g., `deletebox n/Amy b/box-1 b/box-2`                                                                                                                       |
| **Help**       | `help`                                                                                                                                                                                                         |
