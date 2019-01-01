from tkinter import *


class Main:
    def __init__(self):
        root = Tk()

        # ==== Button Function ====
        def on_open_btn():
            print("open button")

        def on_new_btn():
            print("new button")

        def on_save_btn():
            print("save button")

        def on_delete_btn():
            print("delete button")

        def on_convert_btn():
            print("convert button")

        def on_sync_btn():
            print("sync button")

        root.title("Mimo")
        root.resizable(False, False)
        root.iconbitmap('..\images\icon.ico')

        # ==== Frame List ====
        button_frm = Frame(root)
        button_frm.grid(row=1, column=1, columnspan=2)

        list_frm = Frame(root, relief="solid", bd=2)
        list_frm.grid(row=2, column=1)

        main_frm = Frame(root)
        main_frm.grid(row=2, column=2)

        # ==== Button Frame ====
        open_btn = Button(button_frm, width=16, text="Open", command=on_open_btn)
        open_btn.grid(row=1, column=1)

        new_btn = Button(button_frm, width=16, text="New", command=on_new_btn)
        new_btn.grid(row=1, column=2)

        save_btn = Button(button_frm, width=16, text="Save", command=on_save_btn)
        save_btn.grid(row=1, column=3)

        delete_btn = Button(button_frm, width=16, text="Delete", command=on_delete_btn)
        delete_btn.grid(row=1, column=4)

        convert_btn = Button(button_frm, width=16, text="Convert", command=on_convert_btn)
        convert_btn.grid(row=1, column=5)

        sync_btn = Button(button_frm, width=16, text="Sync", command=on_sync_btn)
        sync_btn.grid(row=1, column=6)

        # ==== List Frame ====
        memo_list_lsb = Listbox(list_frm, selectmod="single")
        memo_list_lsb.insert(0, "Test1")
        memo_list_lsb.insert(1, "Test2")
        memo_list_lsb.grid(row=1, column=1)

        # ==== Main Frame ====
        memo_title_ety = Entry(main_frm, width=80)
        memo_title_ety.grid(row=1, column=1)

        memo_txt = Text(main_frm)
        memo_txt.grid(row=2, column=1)

        root.mainloop()


if __name__ == "__main__":
    Main()
