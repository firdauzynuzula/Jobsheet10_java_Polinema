# Auto-push Utility (PowerShell)

File: `scripts/auto_push.ps1`

Ringkasan:
- Skrip ini melakukan `git add -A`, `git commit` (jika ada perubahan), dan `git push` ke branch saat ini.

Cara pakai cepat:

1. Buka PowerShell di dalam folder repository (atau jalankan skrip dari mana pun — skrip akan berpindah ke root repo).
2. Jalankan:

```
.\scripts\auto_push.ps1 -Message "Pesan commit Anda"
```

Jika tidak menyertakan `-Message`, skrip akan membuat pesan otomatis dengan timestamp.

Opsi untuk menggunakan nama file terbuka sebagai pesan commit:

- Gunakan `-UseOpenFile` dan berikan path file lewat `-OpenFile`:

```
.\scripts\auto_push.ps1 -UseOpenFile -OpenFile "src\main\java\Bioskop11.java"
```

- Atau hanya jalankan `-UseOpenFile` tanpa `-OpenFile` — skrip akan mencoba memilih file yang paling baru dimodifikasi di repo:

```
.\scripts\auto_push.ps1 -UseOpenFile
```

Format pesan commit yang dibuat otomatis sekarang:

- Jika menggunakan `-UseOpenFile`, pesan utama akan menjadi: `update: {nama file yang terbuka}`.
- Jika ada file baru yang ditambahkan (staged dengan status `A`), skrip akan menambahkan bagian `menambahkan : {nama file baru}`.
- Jika tidak menggunakan `-UseOpenFile` dan Anda tidak memberikan `-Message`, skrip akan membuat pesan `update: {file yang diubah}` berdasarkan file yang dimodifikasi/staged.

Perilaku tambahan:
- Skrip sekarang menggunakan `git add .` sebelum commit (sesuai permintaan).
- Jika setelah `git add .` tidak ada perubahan, skrip tidak akan membuat commit atau push.

Mengatasi policy eksekusi (jika diblokir):

```
Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned -Force
```

Otentikasi Git:
- Disarankan menggunakan SSH key atau Git Credential Manager agar `git push` tidak meminta username/password tiap kali.

Menambahkan alias git (opsional):

```
git config --global alias.autopush '!powershell -NoProfile -ExecutionPolicy Bypass -File "$(pwd)\\scripts\\auto_push.ps1" -Message "Auto commit"'
```

Catatan scheduling (opsional):
- Untuk menjalankan otomatis berkala di Windows, gunakan Task Scheduler. Contoh perintah PowerShell (jalankan sebagai Admin) untuk membuat task yang menjalankan skrip tiap jam:

```
$action = New-ScheduledTaskAction -Execute 'PowerShell.exe' -Argument '-NoProfile -ExecutionPolicy Bypass -File "C:\Path\To\repo\scripts\auto_push.ps1"'
$trigger = New-ScheduledTaskTrigger -Once -At (Get-Date) -RepetitionInterval (New-TimeSpan -Hours 1) -RepetitionDuration ([TimeSpan]::MaxValue)
Register-ScheduledTask -Action $action -Trigger $trigger -TaskName 'AutoPushEveryHour' -Description 'Auto push repository changes every hour'
```

Pastikan mengganti `C:\Path\To\repo` dengan path repo Anda.
