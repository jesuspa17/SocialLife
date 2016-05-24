# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0023_estaapuntadoa'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='estaapuntadoa',
            name='plan',
        ),
    ]
