from tkinter import *

root = Tk()

root.title("Mimo")
root.resizable(False, False)


# ==== Frame List ====
buttonFrm = Frame(root)
buttonFrm.grid(row=1, column=1, columnspan=2)

listFrm = Frame(root, relief="solid", bd=2)
listFrm.grid(row=2, column=1)

mainFrm = Frame(root)
mainFrm.grid(row=2, column=2)


# ==== Button Frame ====
openBtn = Button(buttonFrm, width=16, text="Open")
openBtn.grid(row=1, column=1)

newBtn = Button(buttonFrm, width=16, text="New")
newBtn.grid(row=1, column=2)

saveBtn = Button(buttonFrm, width=16, text="Save")
saveBtn.grid(row=1, column=3)

deleteBtn = Button(buttonFrm, width=16, text="Delete")
deleteBtn.grid(row=1, column=4)

convertBtn = Button(buttonFrm, width=16, text="Convert")
convertBtn.grid(row=1, column=5)

syncBtn = Button(buttonFrm, width=16, text="Sync")
syncBtn.grid(row=1, column=6)


# ==== List Frame ====
memoListLsb = Listbox(listFrm, selectmod="single")
memoListLsb.insert(0, "Test1")
memoListLsb.insert(1, "Test2")
memoListLsb.grid(row=1, column=1)


# ==== Main Frame ====
memoTitleEty = Entry(mainFrm, width=80)
memoTitleEty.grid(row=1, column=1)

memoTxt = Text(mainFrm)
memoTxt.grid(row=2, column=1)


root.mainloop()
