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
            this.label1 = new System.Windows.Forms.Label();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.btnOpen = new System.Windows.Forms.ToolStripMenuItem();
            this.btnNew = new System.Windows.Forms.ToolStripMenuItem();
            this.btnSave = new System.Windows.Forms.ToolStripMenuItem();
            this.btnDelete = new System.Windows.Forms.ToolStripMenuItem();
            this.btnConvert = new System.Windows.Forms.ToolStripMenuItem();
            this.btnSync = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // mimLists
            // 
            this.mimLists.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(246)))), ((int)(((byte)(244)))), ((int)(((byte)(195)))));
            this.mimLists.FormattingEnabled = true;
            this.mimLists.Location = new System.Drawing.Point(17, 40);
            this.mimLists.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.mimLists.Name = "mimLists";
            this.mimLists.Size = new System.Drawing.Size(114, 316);
            this.mimLists.TabIndex = 0;
            this.mimLists.DoubleClick += new System.EventHandler(this.BtnOpen_Click);
            // 
            // showMemo
            // 
            this.showMemo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(246)))), ((int)(((byte)(244)))), ((int)(((byte)(195)))));
            this.showMemo.Location = new System.Drawing.Point(146, 64);
            this.showMemo.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.showMemo.Multiline = true;
            this.showMemo.Name = "showMemo";
            this.showMemo.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.showMemo.Size = new System.Drawing.Size(472, 292);
            this.showMemo.TabIndex = 1;
            // 
            // fileTitle
            // 
            this.fileTitle.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(246)))), ((int)(((byte)(244)))), ((int)(((byte)(195)))));
            this.fileTitle.Location = new System.Drawing.Point(146, 40);
            this.fileTitle.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.fileTitle.Name = "fileTitle";
            this.fileTitle.Size = new System.Drawing.Size(472, 20);
            this.fileTitle.TabIndex = 1;
            this.fileTitle.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.BackColor = System.Drawing.Color.White;
            this.label1.Enabled = false;
            this.label1.Location = new System.Drawing.Point(344, 42);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(98, 13);
            this.label1.TabIndex = 3;
            this.label1.Text = "제목을 입력하세요";
            this.label1.Visible = false;
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
            this.btnSync});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Padding = new System.Windows.Forms.Padding(3, 1, 0, 1);
            this.menuStrip1.Size = new System.Drawing.Size(636, 24);
            this.menuStrip1.TabIndex = 4;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // btnOpen
            // 
            this.btnOpen.ForeColor = System.Drawing.Color.White;
            this.btnOpen.Name = "btnOpen";
            this.btnOpen.Size = new System.Drawing.Size(50, 22);
            this.btnOpen.Text = "OPEN";
            this.btnOpen.Click += new System.EventHandler(this.BtnOpen_Click);
            // 
            // btnNew
            // 
            this.btnNew.ForeColor = System.Drawing.Color.White;
            this.btnNew.Name = "btnNew";
            this.btnNew.Size = new System.Drawing.Size(45, 22);
            this.btnNew.Text = "NEW";
            this.btnNew.Click += new System.EventHandler(this.BtnNew_Click);
            // 
            // btnSave
            // 
            this.btnSave.ForeColor = System.Drawing.Color.White;
            this.btnSave.Name = "btnSave";
            this.btnSave.Size = new System.Drawing.Size(48, 22);
            this.btnSave.Text = "SAVE";
            this.btnSave.Click += new System.EventHandler(this.BtnSave_Click);
            // 
            // btnDelete
            // 
            this.btnDelete.ForeColor = System.Drawing.Color.White;
            this.btnDelete.Name = "btnDelete";
            this.btnDelete.Size = new System.Drawing.Size(58, 22);
            this.btnDelete.Text = "DELETE";
            this.btnDelete.Click += new System.EventHandler(this.BtnDelete_Click);
            // 
            // btnConvert
            // 
            this.btnConvert.ForeColor = System.Drawing.Color.White;
            this.btnConvert.Name = "btnConvert";
            this.btnConvert.Size = new System.Drawing.Size(72, 22);
            this.btnConvert.Text = "CONVERT";
            this.btnConvert.Click += new System.EventHandler(this.BtnConvert_Click);
            // 
            // btnSync
            // 
            this.btnSync.ForeColor = System.Drawing.Color.White;
            this.btnSync.Name = "btnSync";
            this.btnSync.Size = new System.Drawing.Size(50, 22);
            this.btnSync.Text = "SYNC";
            this.btnSync.Click += new System.EventHandler(this.BtnSync_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(246)))), ((int)(((byte)(244)))), ((int)(((byte)(195)))));
            this.ClientSize = new System.Drawing.Size(636, 371);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.fileTitle);
            this.Controls.Add(this.showMemo);
            this.Controls.Add(this.mimLists);
            this.Controls.Add(this.menuStrip1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.menuStrip1;
            this.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.Name = "Form1";
            this.Text = "Mimo";
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ListBox mimLists;
        private System.Windows.Forms.TextBox showMemo;
        private System.Windows.Forms.TextBox fileTitle;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem btnOpen;
        private System.Windows.Forms.ToolStripMenuItem btnNew;
        private System.Windows.Forms.ToolStripMenuItem btnSave;
        private System.Windows.Forms.ToolStripMenuItem btnDelete;
        private System.Windows.Forms.ToolStripMenuItem btnConvert;
        private System.Windows.Forms.ToolStripMenuItem btnSync;
    }
}

