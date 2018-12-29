﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.IO;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Mimo
{

    public partial class Form1 : Form
    {
        string dirPath = @"C:\Users\ckdgu\Documents\GitHub\Mimo\mimms";
        string filename;

        public Form1()
        {
            InitializeComponent();
            btnSync.Click += BtnSync_Click;
            btnNew.Click += BtnNew_Click;
            btnSave.Click += BtnSave_Click;
            fileTitle.Click += FileTitle_Click;
        }

        private void FileTitle_Click(object sender, EventArgs e)
        {
            if (fileTitle.Text == "")
            {
               // label1.Text("");
            }
        }

        private void BtnSave_Click(object sender, EventArgs e)
        {
            string title = mimLists.SelectedIndex.ToString();
            title = string.Concat(title, ".mimm");
            title = string.Concat('\\', title);
            string filePath = string.Concat(dirPath, title);
            try
            {
                if (System.IO.File.Exists(filePath))
                {
                    System.IO.File.WriteAllText(filePath, showMemo.Text.ToString());
                }
                else
                {
                    MessageBox.Show("");
                    System.IO.File.Create(filePath);

                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message.ToString());
            }
        }

        private void BtnNew_Click(object sender, EventArgs e)
        {
            string title = fileTitle.Text;
            title = string.Concat(title, ".mimm");
            title = string.Concat('\\', title);
            string filePath = string.Concat(dirPath, title);
            try
            {
                System.IO.File.Create(filePath);
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message.ToString());
            }

        }

        private void BtnSync_Click(object sender, EventArgs e)
        {

            //FolderBrowserDialog fbd = new FolderBrowserDialog();

            //if (fbd.ShowDialog() == DialogResult.OK)
            //    dirPath = fbd.SelectedPath;
            mimLists.Items.Clear();
            dirPath = @"C:\Users\ckdgu\Documents\GitHub\Mimo\mimms";
            if (System.IO.Directory.Exists(@dirPath))
            {
                System.IO.DirectoryInfo di = new System.IO.DirectoryInfo(@dirPath);
                foreach (var item in di.GetFiles())
                {
                    if (item.Name.Split('.')[1] == "mimm")
                    {
                        filename = item.Name.Split('.')[0];
                        mimLists.Items.Add(filename);
                    }
                }
            }
        }

        private void FileRead(params string[] UserInfo)
        {
            FileStream fs = new FileStream(@"../mimms/hello.mimm", FileMode.OpenOrCreate, FileAccess.Read);
            StreamReader st = new StreamReader(fs, System.Text.Encoding.UTF8);

            st.BaseStream.Seek(0, SeekOrigin.Begin);

            while (st.Peek() > -1)
            {
                string temp = st.ReadLine();
                //MessageBox.Show(aaa);
            }
            st.Close();
            fs.Close();
        }
        private void FileWrite(string str)
        {
            FileStream fs = new FileStream(@"../mimms/hello.mimm", FileMode.Append, FileAccess.Write);
            //FileMode중 append는 이어쓰기. 파일이 없으면 만든다.

            StreamWriter sw = new StreamWriter(fs, System.Text.Encoding.UTF8);
            sw.WriteLine(str);
            sw.Flush();
            sw.Close();
            fs.Close();
        }

        private void mimLists_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}
