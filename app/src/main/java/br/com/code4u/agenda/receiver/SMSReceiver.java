package br.com.code4u.agenda.receiver;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.code4u.agenda.R;
import br.com.code4u.agenda.dao.AlunoDao;

/**
 * Created by felipepaiva on 27/10/16.
 */

public class SMSReceiver extends BroadcastReceiver {
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
            Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
            byte[] pdu = (byte[]) pdus[0];

            String formato = (String) intent.getSerializableExtra("format");

            SmsMessage sms = SmsMessage.createFromPdu(pdu,formato);
            String telefone = sms.getDisplayOriginatingAddress();
            AlunoDao alunoDao = new AlunoDao(context);
            if(alunoDao.ehAluno(telefone)){
                Toast.makeText(context, "Chegou SMS do aluno", Toast.LENGTH_SHORT).show();
                MediaPlayer player = MediaPlayer.create(context, R.raw.msg);
                player.start();
            }
            alunoDao.close();
        }


    }
}
