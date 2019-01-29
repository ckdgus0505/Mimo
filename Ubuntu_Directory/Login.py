from tkinter import *


class Login:
    def __init__(self):
        root = Tk()

        root.title("Mimo")
        root.resizable(False, False)
        root.iconbitmap('..\images\icon.ico')

        login_frm = Frame(root)
        login_frm.grid(row=1, column=1)

        button_frm = Frame(root)
        button_frm.grid(row=2, column=1)

        name_lbl = Label(login_frm, text="Name")
        name_lbl.grid(row=1, column=1)
        name_ety = Entry(login_frm)
        name_ety.grid(row=1, column=2)

        pw_lbl = Label(login_frm, text="Password")
        pw_lbl.grid(row=2, column=1)
        pw_ety = Entry(login_frm, text="Password")
        pw_ety.grid(row=2, column=2)

        login_btn = Button(button_frm, text="LogIn")
        login_btn.grid(row=1, column=1)

        signup_btn = Button(button_frm, text="SignUp")
        signup_btn.grid(row=1, column=2)

        root.mainloop()


if __name__ == "__main__":
    Login()
