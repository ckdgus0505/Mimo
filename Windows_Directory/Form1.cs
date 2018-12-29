using System;
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
            btnDelete.Click += BtnDelete_Click;
            btnOpen.Click += BtnOpen_Click;
            fileTitle.Click += FileTitle_Click;
        }

        private void BtnOpen_Click(object sender, EventArgs e)
        {
            string title = mimLists.SelectedItem.ToString();
            title = string.Concat(title, ".mimm");
            title = string.Concat('\\', title);
            string filePath = string.Concat(dirPath, title);
            string text = System.IO.File.ReadAllText(filePath, Encoding.UTF8);
            showMemo.Text = text;
            fileTitle.Text = mimLists.SelectedItem.ToString();
        }

        private void BtnDelete_Click(object sender, EventArgs e)
        {
            string title = mimLists.SelectedItem.ToString();
            title = string.Concat(title, ".mimm");
            title = string.Concat('\\', title);
            string filePath = string.Concat(dirPath, title);
            if (MessageBox.Show("선택한 메모를 삭제하시겠습니까?", "메모삭제",MessageBoxButtons.YesNo)==DialogResult.Yes)
            {
                if(System.IO.File.Exists(filePath))
                {
                    try
                    {
                        System.IO.File.Delete(filePath);
                    }
                    catch (System.IO.IOException err)
                    {
                        MessageBox.Show(err.ToString());
                        return;
                    }
                }




                mimLists.Items.Remove(mimLists.SelectedItem);
            }
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
            string title = mimLists.SelectedItem.ToString();
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

        private void mimLists_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}
