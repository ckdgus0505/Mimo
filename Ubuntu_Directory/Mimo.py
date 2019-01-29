# coding=utf-8
from tkinter import *   # Python GUI를 만들기 위함
import glob             # 디렉터리에 있는 파일들을 리스트로 만들기 위함
import os               # 파일 삭제를 하기 위함


class Main:
    def __init__(self):
        # GUI 시작
        root = Tk()

        # ==== Button Function ====
        def on_open_btn():
            # 처음에 삭제 해주고 시작
            memo_title_ety.delete(0, 100)
            memo_txt.delete(0.0, 100.0)

            index = str(memo_list_lsb.curselection())[1:-2]
            file_name = str(memo_list_lsb.get(index, index))[2:-3]

            # Ety 부분
            memo_title_ety.insert(0, file_name)

            # Txt 부분
            f = open("..\mimms\\" + file_name + ".mimm", "r")
            i = float(0.0)
            while True:
                line = f.readline()
                if not line: break
                memo_txt.insert(str(i), line)
                i += float(1.0)
            f.close()

            on_sync_btn()

        def on_new_btn():
            f = open("..\mimms\\" + memo_title_ety.get() + ".mimm", "w")
            f.write(memo_txt.get(0.0, 100.0))   # 0번째 줄 부터 100번째 줄 까지
            f.close()
            on_sync_btn()

        def on_save_btn():
            f = open("..\mimms\\" + memo_title_ety.get() + ".mimm", "w")
            f.write(memo_txt.get(0.0, 100.0))  # 0번째 줄 부터 100번째 줄 까지
            f.close()
            on_sync_btn()

        def on_delete_btn():
            index = str(memo_list_lsb.curselection())[1:-2]
            file_name = str(memo_list_lsb.get(index, index))[2:-3]
            os.remove("..\mimms\\" + file_name + ".mimm")
            on_sync_btn()

        def on_convert_btn():
            print("convert button")

        def on_sync_btn():
            # 디렉터리에 있는 파일들 가져오기
            memo_list_lsb.delete(0, memo_list_lsb.size())
            mimms = glob.glob("..\mimms\*.mimm")
            for i in range(len(mimms)):
                memo_list_lsb.insert(i, mimms[i][9:-5])  # 경로 및 확장자 제거

        root.title("Mimo")
        root.resizable(False, False)
        # root.iconbitmap('..\images\icon.ico')

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
        memo_list_lsb.grid(row=1, column=1)
        on_sync_btn()

        # ==== Main Frame ====
        memo_title_ety = Entry(main_frm, width=80)
        memo_title_ety.grid(row=1, column=1)

        memo_txt = Text(main_frm)
        memo_txt.grid(row=2, column=1)

        root.mainloop()


if __name__ == "__main__":
    Main()
