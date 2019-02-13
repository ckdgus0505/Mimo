from tkinter import *
import tkinter.messagebox
import webbrowser
import Mimo     # Mimo.py
import pymysql


class Login:
    def __init__(self):
        root = Tk()

        def on_login_btn():
            conn = pymysql.connect(host='mimm.dlinkddns.com', user='MIMO', password='mimo', db='MIMO', charset='utf8')
            curs = conn.cursor()

            name = name_ety.get()
            sql = "select PASSWORD from MEMBER where ID = '" + name + "'"
            curs.execute(sql)
            password_mysql = str(curs.fetchone())[2:-3]
            password_tkinter = pw_ety.get()

            if password_mysql == password_tkinter and password_mysql != '':
                root.destroy()
                Mimo.Main()

            else:
                tkinter.messagebox.showerror("로그인 에러", "비밀번호 틀림")

        def on_signup_btn():
            webbrowser.open('http://mimm.dlinkddns.com/signup.html')

        # ================================================================================================================================

        root.title("Mimo")
        root.resizable(False, False)
        # root.iconbitmap('..\images\icon.ico')

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

        login_btn = Button(button_frm, text="LogIn", command=on_login_btn)
        login_btn.grid(row=1, column=1)

        signup_btn = Button(button_frm, text="SignUp", command=on_signup_btn)
        signup_btn.grid(row=1, column=2)

        root.mainloop()


if __name__ == "__main__":
    Login()
