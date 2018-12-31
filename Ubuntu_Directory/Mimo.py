from tkinter import *

root = Tk()

root.title("Mimo")


# ==== Frame List ====
buttonFrm = Frame(root)
buttonFrm.grid(row=1, column=1)

mainFrm = Frame(root)
mainFrm.grid(row=2, column=1)


# ==== Button Frame ====
openBtn = Button(buttonFrm, text="Open")
openBtn.grid(row=1, column=1)

newBtn = Button(buttonFrm, text="New")
newBtn.grid(row=1, column=2)

saveBtn = Button(buttonFrm, text="Save")
saveBtn.grid(row=1, column=3)

deleteBtn = Button(buttonFrm, text="Delete")
deleteBtn.grid(row=1, column=4)

convertBtn = Button(buttonFrm, text="Convert")
convertBtn.grid(row=1, column=5)

syncBtn = Button(buttonFrm, text="Sync")
syncBtn.grid(row=1, column=6)


# ==== Main Frame ====
memoListLbl = Label(mainFrm, text="test\ntest")
memoListLbl.grid(row=1, column=1)

memoTitleEty = Entry(mainFrm)
memoTitleEty.grid(row=1, column=2)

memoEty = Entry(mainFrm)
memoEty.grid(row=2, column=2)

root.mainloop()
