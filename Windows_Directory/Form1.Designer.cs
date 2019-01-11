namespace Mimo
{
    partial class Form1
    {
        /// <summary>
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 디자이너에서 생성한 코드

        /// <summary>
        /// 디자이너 지원에 필요한 메서드입니다. 
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마세요.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.mimLists = new System.Windows.Forms.ListBox();
            this.showMemo = new System.Windows.Forms.TextBox();
            this.fileTitle = new System.Windows.Forms.TextBox();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.btnOpen = new System.Windows.Forms.ToolStripMenuItem();
            this.btnNew = new System.Windows.Forms.ToolStripMenuItem();
            this.btnSave = new System.Windows.Forms.ToolStripMenuItem();
            this.btnDelete = new System.Windows.Forms.ToolStripMenuItem();
            this.btnConvert = new System.Windows.Forms.ToolStripMenuItem();
            this.btnSync = new System.Windows.Forms.ToolStripMenuItem();
            this.btnHelp = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // mimLists
            // 
            this.mimLists.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.mimLists.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(246)))), ((int)(((byte)(244)))), ((int)(((byte)(195)))));
            this.mimLists.FormattingEnabled = true;
            this.mimLists.ItemHeight = 25;
            this.mimLists.Location = new System.Drawing.Point(40, 80);
            this.mimLists.Margin = new System.Windows.Forms.Padding(40, 40, 20, 40);
            this.mimLists.Name = "mimLists";
            this.mimLists.Size = new System.Drawing.Size(224, 604);
            this.mimLists.TabIndex = 0;
            this.mimLists.DoubleClick += new System.EventHandler(this.BtnOpen_Click);
            // 
            // showMemo
            // 
            this.showMemo.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.showMemo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(246)))), ((int)(((byte)(244)))), ((int)(((byte)(195)))));
            this.showMemo.Location = new System.Drawing.Point(304, 151);
            this.showMemo.Margin = new System.Windows.Forms.Padding(20, 20, 40, 40);
            this.showMemo.Multiline = true;
            this.showMemo.Name = "showMemo";
            this.showMemo.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.showMemo.Size = new System.Drawing.Size(928, 522);
            this.showMemo.TabIndex = 1;
            // 
            // fileTitle
            // 
            this.fileTitle.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.fileTitle.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(246)))), ((int)(((byte)(244)))), ((int)(((byte)(195)))));
            this.fileTitle.Location = new System.Drawing.Point(304, 80);
            this.fileTitle.Margin = new System.Windows.Forms.Padding(20, 40, 40, 20);
            this.fileTitle.Name = "fileTitle";
            this.fileTitle.Size = new System.Drawing.Size(928, 31);
            this.fileTitle.TabIndex = 1;
            this.fileTitle.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // menuStrip1
            // 
            this.menuStrip1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(30)))), ((int)(((byte)(68)))), ((int)(((byte)(115)))));
            this.menuStrip1.ImageScalingSize = new System.Drawing.Size(32, 32);
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.btnOpen,
            this.btnNew,
            this.btnSave,
            this.btnDelete,
            this.btnConvert,
            this.btnSync,
            this.btnHelp});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(1272, 40);
            this.menuStrip1.TabIndex = 4;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // btnOpen
            // 
            this.btnOpen.ForeColor = System.Drawing.Color.White;
            this.btnOpen.Name = "btnOpen";
            this.btnOpen.Padding = new System.Windows.Forms.Padding(25, 0, 25, 0);
            this.btnOpen.Size = new System.Drawing.Size(132, 36);
            this.btnOpen.Text = "OPEN";
            this.btnOpen.Click += new System.EventHandler(this.BtnOpen_Click);
            // 
            // btnNew
            // 
            this.btnNew.ForeColor = System.Drawing.Color.White;
            this.btnNew.Name = "btnNew";
            this.btnNew.Padding = new System.Windows.Forms.Padding(25, 0, 25, 0);
            this.btnNew.Size = new System.Drawing.Size(122, 36);
            this.btnNew.Text = "NEW";
            this.btnNew.Click += new System.EventHandler(this.BtnNew_Click);
            // 
            // btnSave
            // 
            this.btnSave.ForeColor = System.Drawing.Color.White;
            this.btnSave.Name = "btnSave";
            this.btnSave.Padding = new System.Windows.Forms.Padding(25, 0, 25, 0);
            this.btnSave.Size = new System.Drawing.Size(125, 36);
            this.btnSave.Text = "SAVE";
            this.btnSave.Click += new System.EventHandler(this.BtnSave_Click);
            // 
            // btnDelete
            // 
            this.btnDelete.ForeColor = System.Drawing.Color.White;
            this.btnDelete.Name = "btnDelete";
            this.btnDelete.Padding = new System.Windows.Forms.Padding(25, 0, 25, 0);
            this.btnDelete.Size = new System.Drawing.Size(147, 36);
            this.btnDelete.Text = "DELETE";
            this.btnDelete.Click += new System.EventHandler(this.BtnDelete_Click);
            // 
            // btnConvert
            // 
            this.btnConvert.ForeColor = System.Drawing.Color.White;
            this.btnConvert.Name = "btnConvert";
            this.btnConvert.Padding = new System.Windows.Forms.Padding(25, 0, 25, 0);
            this.btnConvert.Size = new System.Drawing.Size(176, 36);
            this.btnConvert.Text = "CONVERT";
            this.btnConvert.Click += new System.EventHandler(this.BtnConvert_Click);
            // 
            // btnSync
            // 
            this.btnSync.ForeColor = System.Drawing.Color.White;
            this.btnSync.Name = "btnSync";
            this.btnSync.Padding = new System.Windows.Forms.Padding(25, 0, 25, 0);
            this.btnSync.Size = new System.Drawing.Size(129, 36);
            this.btnSync.Text = "SYNC";
            this.btnSync.Click += new System.EventHandler(this.BtnSync_Click);
            // 
            // btnHelp
            // 
            this.btnHelp.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
            this.btnHelp.ForeColor = System.Drawing.Color.White;
            this.btnHelp.Name = "btnHelp";
            this.btnHelp.Size = new System.Drawing.Size(38, 36);
            this.btnHelp.Text = "?";
            this.btnHelp.Click += new System.EventHandler(this.BtnHelp_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(246)))), ((int)(((byte)(244)))), ((int)(((byte)(195)))));
            this.ClientSize = new System.Drawing.Size(1272, 713);
            this.Controls.Add(this.fileTitle);
            this.Controls.Add(this.showMemo);
            this.Controls.Add(this.mimLists);
            this.Controls.Add(this.menuStrip1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.menuStrip1;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "Form1";
            this.Text = "Mimo";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ListBox mimLists;
        private System.Windows.Forms.TextBox showMemo;
        private System.Windows.Forms.TextBox fileTitle;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem btnOpen;
        private System.Windows.Forms.ToolStripMenuItem btnNew;
        private System.Windows.Forms.ToolStripMenuItem btnSave;
        private System.Windows.Forms.ToolStripMenuItem btnDelete;
        private System.Windows.Forms.ToolStripMenuItem btnConvert;
        private System.Windows.Forms.ToolStripMenuItem btnSync;
        private System.Windows.Forms.ToolStripMenuItem btnwhatisit;
        private System.Windows.Forms.ToolStripMenuItem btnHelp;
    }
}

