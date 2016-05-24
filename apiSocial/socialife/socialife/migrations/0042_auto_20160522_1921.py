# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0041_auto_20160521_0556'),
    ]

    operations = [
        migrations.AlterField(
            model_name='amistades',
            name='usuario_amigo',
            field=models.ForeignKey(related_name='usuario_amigo', to='socialife.Usuario'),
        ),
        migrations.AlterField(
            model_name='amistades',
            name='usuario_principal',
            field=models.ForeignKey(related_name='usuario_principal', to='socialife.Usuario'),
        ),
    ]
